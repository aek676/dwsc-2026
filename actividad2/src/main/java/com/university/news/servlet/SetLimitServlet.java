package com.university.news.servlet;

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

public class SetLimitServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String limiteStr = request.getParameter("numero_maximo");
            if (limiteStr == null || limiteStr.trim().isEmpty()) {
                out.println("<html><head><title>Error</title></head><body>");
                out.println("<h2 style='color:red'>Error: parametro 'numero_maximo' es requerido</h2>");
                out.println("<a href='consumer.html'>Volver</a>");
                out.println("</body></html>");
                return;
            }

            int nuevoLimite;
            try {
                nuevoLimite = Integer.parseInt(limiteStr.trim());
            } catch (NumberFormatException e) {
                out.println("<html><head><title>Error</title></head><body>");
                out.println("<h2 style='color:red'>Error: el limite debe ser un numero valido</h2>");
                out.println("<a href='consumer.html'>Volver</a>");
                out.println("</body></html>");
                return;
            }

            if (nuevoLimite <= 0) {
                out.println("<html><head><title>Error</title></head><body>");
                out.println("<h2 style='color:red'>Error: el limite debe ser un numero positivo</h2>");
                out.println("<a href='consumer.html'>Volver</a>");
                out.println("</body></html>");
                return;
            }

            java.util.Properties props = new java.util.Properties();
            props.put("org.omg.CORBA.ORBInitialPort", "1050");
            props.put("org.omg.CORBA.ORBInitialHost", "localhost");
            ORB orb = ORB.init(new String[0], props);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            NewsBuffer buffer = NewsBufferHelper.narrow(ncRef.resolve_str("NewsBuffer"));

            int limiteAnterior = buffer.getLimit();
            int noticiasAntes = buffer.getCount();
            buffer.fijarLimiteNoticias(nuevoLimite);
            int limiteActual = buffer.getLimit();
            int noticiasDespues = buffer.getCount();

            out.println("<html><head><title>Resultado Fijar Limite</title></head><body>");
            out.println("<h2 style='color:green'>Limite actualizado correctamente</h2>");
            out.println("<table border='1' cellpadding='5'>");
            out.println("<tr><td><strong>Limite anterior:</strong></td><td>" + limiteAnterior + "</td></tr>");
            out.println("<tr><td><strong>Noticias antes:</strong></td><td>" + noticiasAntes + "</td></tr>");
            out.println("<tr><td><strong>Nuevo limite:</strong></td><td>" + limiteActual + "</td></tr>");
            out.println("<tr><td><strong>Noticias despues:</strong></td><td>" + noticiasDespues + "</td></tr>");
            out.println("</table>");
            if (noticiasAntes > noticiasDespues) {
                out.println("<p style='color:orange'><strong>Se eliminaron " + (noticiasAntes - noticiasDespues) + " noticia(s) porque el nuevo limite es menor.</strong></p>");
            }
            out.println("<br><a href='consumer.html'>Volver al consumidor</a> | ");
            out.println("<a href='producer.html'>Ir a productor</a>");
            out.println("</body></html>");

        } catch (Exception e) {
            out.println("<html><head><title>Error</title></head><body>");
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
            out.println("<a href='consumer.html'>Volver</a>");
            out.println("</body></html>");
        }
    }
}
