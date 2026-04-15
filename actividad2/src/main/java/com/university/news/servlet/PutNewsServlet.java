package com.university.news.servlet;

import com.university.news.model.Noticia;
import com.university.news.xml.XMLCoder;
import NewsBufferApp.NewsBuffer;
import NewsBufferApp.NewsBufferHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class PutNewsServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            String fecha = request.getParameter("fecha");
            String nivel = request.getParameter("nivel");
            String descripcionCorta = request.getParameter("descripcionCorta");
            String descripcionLarga = request.getParameter("descripcionLarga");
            String etiquetasRaw = request.getParameter("etiquetas");
            
            List<String> etiquetas = null;
            if (etiquetasRaw != null && !etiquetasRaw.trim().isEmpty()) {
                etiquetas = Arrays.asList(etiquetasRaw.trim().split("\\s+"));
            }
            
            Noticia noticia = new Noticia(fecha, nivel, descripcionCorta, descripcionLarga, etiquetas);
            List<String> errores = noticia.validar();
            
            if (!errores.isEmpty()) {
                out.println("<html><head><title>Error</title></head><body>");
                out.println("<h2>Errores de validacion:</h2>");
                out.println("<ul>");
                for (String error : errores) {
                    out.println("<li>" + error + "</li>");
                }
                out.println("</ul>");
                out.println("<a href='producer.html'>Volver</a>");
                out.println("</body></html>");
                return;
            }
            
            String xmlNoticia = XMLCoder.encode(noticia);
            
            String orbHost = System.getenv("ORB_HOST") != null ? System.getenv("ORB_HOST") : "localhost";
            String orbPort = System.getenv("ORB_PORT") != null ? System.getenv("ORB_PORT") : "1050";
            java.util.Properties props = new java.util.Properties();
            props.put("org.omg.CORBA.ORBInitialPort", orbPort);
            props.put("org.omg.CORBA.ORBInitialHost", orbHost);
            ORB orb = ORB.init(new String[0], props);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            NewsBuffer buffer = NewsBufferHelper.narrow(ncRef.resolve_str("NewsBuffer"));
            
            boolean resultado = buffer.put(xmlNoticia);
            
            out.println("<html><head><title>Resultado PUT</title></head><body>");
            if (resultado) {
                out.println("<h2 style='color:green'>Noticia insertada correctamente</h2>");
                out.println("<p><strong>Noticia:</strong></p>");
                out.println("<pre>" + xmlNoticia + "</pre>");
            } else {
                out.println("<h2 style='color:red'>Error: Buffer lleno</h2>");
                out.println("<p>No se puede insertar. El buffer ha alcanzado su capacidad maxima.</p>");
            }
            out.println("<br><a href='producer.html'>Insertar otra</a> | ");
            out.println("<a href='consumer.html'>Ir a consumidor</a>");
            out.println("</body></html>");
            
        } catch (Exception e) {
            out.println("<html><head><title>Error</title></head><body>");
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
            out.println("<a href='producer.html'>Volver</a>");
            out.println("</body></html>");
        }
    }
}
