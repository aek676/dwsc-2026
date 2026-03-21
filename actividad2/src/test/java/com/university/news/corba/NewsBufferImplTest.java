package com.university.news.corba;

import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.StringHolder;
import static org.junit.Assert.*;

public class NewsBufferImplTest {

    private NewsBufferImpl buffer;

    @Before
    public void setUp() {
        buffer = new NewsBufferImpl();
    }

    @Test
    public void testLimiteInicial() {
        assertEquals(20, buffer.getLimit());
    }

    @Test
    public void testFijarLimiteMayorQueActual() {
        buffer.put("noticia1");
        buffer.put("noticia2");
        buffer.fijarLimiteNoticias(50);
        assertEquals(50, buffer.getLimit());
        assertEquals(2, buffer.getCount());
    }

    @Test
    public void testFijarLimiteIgualQueActual() {
        buffer.put("noticia1");
        buffer.put("noticia2");
        buffer.put("noticia3");
        buffer.fijarLimiteNoticias(3);
        assertEquals(3, buffer.getLimit());
        assertEquals(3, buffer.getCount());
    }

    @Test
    public void testFijarLimiteMenorQueCardinalidad() {
        for (int i = 0; i < 5; i++) {
            buffer.put("noticia" + i);
        }
        assertEquals(5, buffer.getCount());

        buffer.fijarLimiteNoticias(3);
        assertEquals(3, buffer.getLimit());
        assertEquals(3, buffer.getCount());
    }

    @Test
    public void testFijarLimiteRecorteCorrecto() {
        for (int i = 0; i < 5; i++) {
            buffer.put("noticia" + i);
        }

        buffer.fijarLimiteNoticias(3);

        StringHolder h = new StringHolder();
        assertTrue(buffer.get(h));
        assertEquals("noticia0", h.value);
        assertTrue(buffer.get(h));
        assertEquals("noticia1", h.value);
        assertTrue(buffer.get(h));
        assertEquals("noticia2", h.value);
        assertTrue(buffer.isEmpty());
        assertEquals(0, buffer.getCount());
    }

    @Test
    public void testFijarLimiteANegativo() {
        buffer.put("noticia1");
        buffer.put("noticia2");
        buffer.fijarLimiteNoticias(-5);
        assertEquals(0, buffer.getLimit());
        assertEquals(0, buffer.getCount());
    }

    @Test
    public void testFijarLimiteAUno() {
        for (int i = 0; i < 5; i++) {
            buffer.put("noticia" + i);
        }

        buffer.fijarLimiteNoticias(1);
        assertEquals(1, buffer.getLimit());
        assertEquals(1, buffer.getCount());

        StringHolder h = new StringHolder();
        assertTrue(buffer.get(h));
        assertEquals("noticia0", h.value);
        assertTrue(buffer.isEmpty());
    }

    @Test
    public void testPutDespuesDeFijarLimite() {
        buffer.fijarLimiteNoticias(2);
        assertTrue(buffer.put("noticia1"));
        assertTrue(buffer.put("noticia2"));
        assertFalse(buffer.put("noticia3"));
        assertEquals(2, buffer.getCount());
    }

    @Test
    public void testFijarLimiteBufferVacio() {
        assertTrue(buffer.isEmpty());
        buffer.fijarLimiteNoticias(5);
        assertEquals(5, buffer.getLimit());
        assertEquals(0, buffer.getCount());
    }

    @Test
    public void testGetReadConBufferNoVacio() {
        buffer.put("noticia1");
        buffer.put("noticia2");

        StringHolder h = new StringHolder();
        assertTrue(buffer.read(h));
        assertEquals("noticia1", h.value);
        assertEquals(2, buffer.getCount());

        assertTrue(buffer.get(h));
        assertEquals("noticia1", h.value);
        assertEquals(1, buffer.getCount());
    }

    @Test
    public void testGetReadBufferVacio() {
        StringHolder h = new StringHolder();
        assertFalse(buffer.get(h));
        assertEquals("", h.value);
        assertFalse(buffer.read(h));
        assertEquals("", h.value);
    }

    @Test
    public void testIsNotEmpty() {
        assertTrue(buffer.isEmpty());
        buffer.put("noticia1");
        assertFalse(buffer.isEmpty());
    }

    @Test
    public void testFijarLimiteRecorteYVerificarFIFO() {
        for (int i = 0; i < 20; i++) {
            buffer.put("noticia" + i);
        }
        assertEquals(20, buffer.getCount());

        buffer.fijarLimiteNoticias(12);
        assertEquals(12, buffer.getCount());

        StringHolder h = new StringHolder();
        for (int i = 0; i < 12; i++) {
            assertTrue(buffer.get(h));
            assertEquals("noticia" + i, h.value);
        }
        assertTrue(buffer.isEmpty());
    }
}
