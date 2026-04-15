package com.university.news.servlet;

import com.university.news.model.Noticia;
import com.university.news.xml.XMLDecoder;
import NewsBufferApp.NewsBuffer;
import NewsBufferApp.NewsBufferHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GetNewsServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            String orbHost = System.getenv("ORB_HOST") != null ? System.getenv("ORB_HOST") : "localhost";
            String orbPort = System.getenv("ORB_PORT") != null ? System.getenv("ORB_PORT") : "1050";
            java.util.Properties props = new java.util.Properties();
            props.put("org.omg.CORBA.ORBInitialPort", orbPort);
            props.put("org.omg.CORBA.ORBInitialHost", orbHost);
            ORB orb = ORB.init(new String[0], props);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            NewsBuffer buffer = NewsBufferHelper.narrow(ncRef.resolve_str("NewsBuffer"));
            
            StringHolder elemento = new StringHolder();
            boolean resultado = buffer.get(elemento);
            
            out.println("<html><head><title>Resultado GET</title></head><body>");
            if (resultado) {
                out.println("<h2>Noticia extraida (eliminada del buffer)</h2>");
                out.println("<pre>" + elemento.value + "</pre>");
                out.println("<h3>Datos decodificados:</h3>");
                try {
                    Noticia noticia = XMLDecoder.decode(elemento.value);
                    out.println("<table border='1' cellpadding='5'>");
                    out.println("<tr><td><strong>Fecha:</strong></td><td>" + noticia.getFecha() + "</td></tr>");
                    out.println("<tr><td><strong>Nivel:</strong></td><td>" + noticia.getNivel() + "</td></tr>");
                    out.println("<tr><td><strong>Desc. Corta:</strong></td><td>" + noticia.getDescripcionCorta() + "</td></tr>");
                    out.println("<tr><td><strong>Desc. Larga:</strong></td><td>" + noticia.getDescripcionLarga() + "</td></tr>");
                    out.println("<tr><td><strong>Etiquetas:</strong></td><td>" + noticia.getEtiquetas() + "</td></tr>");
                    out.println("</table>");
                } catch (Exception ex) {
                    out.println("<p>No se pudo decodificar: " + ex.getMessage() + "</p>");
                }
            } else {
                out.println("<h2 style='color:red'>Error: Buffer vacio</h2>");
                out.println("<p>No hay noticias para extraer.</p>");
            }
            out.println("<br><a href='consumer.html'>Volver</a>");
            out.println("</body></html>");
            
        } catch (Exception e) {
            out.println("<html><head><title>Error</title></head><body>");
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
            out.println("<a href='consumer.html'>Volver</a>");
            out.println("</body></html>");
        }
    }
}
