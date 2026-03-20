package com.university.news.corba;

import NewsBufferApp.NewsBuffer;
import NewsBufferApp.NewsBufferHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

public class NewsBufferClient {
    static NewsBuffer bufferImpl;

    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            
            String name = "NewsBuffer";
            bufferImpl = NewsBufferHelper.narrow(ncRef.resolve_str(name));
            
            System.out.println("Cliente NewsBuffer conectado.");
            System.out.println("Buffer vacio: " + bufferImpl.isEmpty());
            System.out.println("Numero de elementos: " + bufferImpl.getCount());
            
            StringHolder elemento = new StringHolder();
            
            System.out.println("\n--- Probando PUT ---");
            String xml = "<noticia><fecha>20/03/2026</fecha><nivel>alta</nivel>" +
                        "<descripcionCorta>Noticiaejemplo</descripcionCorta>" +
                        "<descripcionLarga>Descripcion largade ejemplo20 chars</descripcionLarga>" +
                        "<etiquetas><etiqueta>#test</etiqueta></etiquetas></noticia>";
            System.out.println("PUT result: " + bufferImpl.put(xml));
            System.out.println("Elementos: " + bufferImpl.getCount());
            
            System.out.println("\n--- Probando READ ---");
            if (bufferImpl.read(elemento)) {
                System.out.println("Elemento leido: " + elemento.value);
            }
            
            System.out.println("\n--- Probando GET ---");
            if (bufferImpl.get(elemento)) {
                System.out.println("Elemento extraido: " + elemento.value);
            }
            
            System.out.println("\n--- Estado final ---");
            System.out.println("Buffer vacio: " + bufferImpl.isEmpty());
            System.out.println("Numero de elementos: " + bufferImpl.getCount());
            
            bufferImpl.shutdown();
            
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }
    }
}
