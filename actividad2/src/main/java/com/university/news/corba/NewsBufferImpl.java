package com.university.news.corba;

import NewsBufferApp._NewsBufferImplBase;
import org.omg.CORBA.StringHolder;

public class NewsBufferImpl extends _NewsBufferImplBase {
    private String[] buffer;
    private int elementos;
    private int maxElementos;
    private static final int INITIAL_MAX = 20;
    private org.omg.CORBA.ORB orb;

    public NewsBufferImpl() {
        maxElementos = INITIAL_MAX;
        buffer = new String[maxElementos];
        elementos = 0;
    }

    public void setORB(org.omg.CORBA.ORB orb_val) {
        orb = orb_val;
    }

    public boolean put(String noticiaXML) {
        if (elementos < maxElementos) {
            buffer[elementos] = noticiaXML;
            elementos++;
            System.out.println("PUT: Elemento insertado. Total: " + elementos + "/" + maxElementos);
            return true;
        } else {
            System.out.println("PUT: Buffer LLENO (" + elementos + "/" + maxElementos + "). No se puede insertar.");
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
            System.out.println("GET: Elemento extraido. Total: " + elementos + "/" + maxElementos);
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

    public int getLimit() {
        return maxElementos;
    }

    public void fijarLimiteNoticias(int numero_maximo) {
        if (numero_maximo < 0) {
            numero_maximo = 0;
        }
        maxElementos = numero_maximo;
        if (elementos > maxElementos) {
            int eliminados = elementos - maxElementos;
            for (int i = maxElementos; i < elementos; i++) {
                buffer[i] = null;
            }
            elementos = maxElementos;
            System.out.println("LIMITE FIJADO: Buffer recortado de " + (elementos + eliminados) + " a " + elementos + " (limite: " + maxElementos + ")");
        } else {
            System.out.println("LIMITE FIJADO: " + maxElementos + " (elementos actuales: " + elementos + ")");
        }
    }

    public void shutdown() {
        System.out.println("Cerrando servidor NewsBuffer...");
        if (orb != null) {
            orb.shutdown(false);
        }
    }
}
