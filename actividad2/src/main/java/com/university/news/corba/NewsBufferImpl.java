package com.university.news.corba;

import NewsBufferApp.NewsBuffer;
import NewsBufferApp._NewsBufferImplBase;
import org.omg.CORBA.StringHolder;

public class NewsBufferImpl extends _NewsBufferImplBase {
    private String[] buffer;
    private int elementos;
    private static final int MAX_ELEMENTOS = 20;
    private org.omg.CORBA.ORB orb;

    public NewsBufferImpl() {
        buffer = new String[MAX_ELEMENTOS];
        elementos = 0;
    }

    public void setORB(org.omg.CORBA.ORB orb_val) {
        orb = orb_val;
    }

    public boolean put(String noticiaXML) {
        if (elementos < MAX_ELEMENTOS) {
            buffer[elementos] = noticiaXML;
            elementos++;
            System.out.println("PUT: Elemento insertado. Total: " + elementos);
            System.out.println("Contenido: " + noticiaXML);
            return true;
        } else {
            System.out.println("PUT: Buffer LLENO. No se puede insertar.");
            return false;
        }
    }

    public boolean get(StringHolder noticiaXML) {
        if (elementos > 0) {
            noticiaXML.value = buffer[0];
            for (int i = 0; i < elementos - 1; i++) {
                buffer[i] = buffer[i + 1];
            }
            elementos--;
            System.out.println("GET: Elemento extraido. Total: " + elementos);
            return true;
        } else {
            noticiaXML.value = "";
            System.out.println("GET: Buffer VACIO. No se puede extraer.");
            return false;
        }
    }

    public boolean read(StringHolder noticiaXML) {
        if (elementos > 0) {
            noticiaXML.value = buffer[0];
            System.out.println("READ: Elemento en tope: " + buffer[0]);
            return true;
        } else {
            noticiaXML.value = "";
            System.out.println("READ: Buffer VACIO.");
            return false;
        }
    }

    public boolean isEmpty() {
        return elementos == 0;
    }

    public int getCount() {
        return elementos;
    }

    public void shutdown() {
        System.out.println("Cerrando servidor NewsBuffer...");
        if (orb != null) {
            orb.shutdown(false);
        }
    }
}
