package com.university.news.model;

import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class NoticiaTest {
    
    private Noticia noticiaValida;
    
    @Before
    public void setUp() {
        noticiaValida = new Noticia(
            "20/03/2026",
            "alta",
            "Noticiaejemplo",
            "Descripcionlarga de prueba conmas de20 caracteres",
            Arrays.asList("#musica", "#festivalAlmeria")
        );
    }
    
    @Test
    public void testNoticiaValida() {
        assertTrue(noticiaValida.esValida());
        assertTrue(noticiaValida.validar().isEmpty());
    }
    
    @Test
    public void testValidarFechaCorrecta() {
        assertTrue(noticiaValida.validarFecha());
    }
    
    @Test
    public void testValidarFechaInvalida() {
        Noticia n = new Noticia();
        n.setFecha("01/01/2026");
        assertTrue(n.validarFecha());
    }
    
    @Test
    public void testValidarFechaInvalidaFormato() {
        Noticia n = new Noticia();
        n.setFecha("2026-03-20");
        assertFalse(n.validarFecha());
    }
    
    @Test
    public void testValidarFechaVacia() {
        Noticia n = new Noticia();
        n.setFecha(null);
        assertFalse(n.validarFecha());
    }
    
    @Test
    public void testValidarNivelAlta() {
        assertTrue(noticiaValida.validarNivel());
    }
    
    @Test
    public void testValidarNivelMedia() {
        Noticia n = new Noticia();
        n.setNivel("media");
        assertTrue(n.validarNivel());
    }
    
    @Test
    public void testValidarNivelBaja() {
        Noticia n = new Noticia();
        n.setNivel("baja");
        assertTrue(n.validarNivel());
    }
    
    @Test
    public void testValidarNivelInvalido() {
        Noticia n = new Noticia();
        n.setNivel("muyalta");
        assertFalse(n.validarNivel());
    }
    
    @Test
    public void testValidarDescripcionCortaValida() {
        assertTrue(noticiaValida.validarDescripcionCorta());
    }
    
    @Test
    public void testValidarDescripcionCortaMuyCorta() {
        Noticia n = new Noticia();
        n.setDescripcionCorta("abc");
        assertFalse(n.validarDescripcionCorta());
    }
    
    @Test
    public void testValidarDescripcionCortaMuyLarga() {
        Noticia n = new Noticia();
        n.setDescripcionCorta("abcdefghijklmnopqrstuvwxyz12345");
        assertFalse(n.validarDescripcionCorta());
    }
    
    @Test
    public void testValidarDescripcionLargaValida() {
        assertTrue(noticiaValida.validarDescripcionLarga());
    }
    
    @Test
    public void testValidarDescripcionLargaMuyCorta() {
        Noticia n = new Noticia();
        n.setDescripcionLarga("corta");
        assertFalse(n.validarDescripcionLarga());
    }
    
    @Test
    public void testValidarDescripcionLargaMuyLarga() {
        Noticia n = new Noticia();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 260; i++) sb.append("a");
        n.setDescripcionLarga(sb.toString());
        assertFalse(n.validarDescripcionLarga());
    }
    
    @Test
    public void testValidarEtiquetasValidas() {
        assertTrue(noticiaValida.validarEtiquetas());
    }
    
    @Test
    public void testValidarEtiquetasSinHash() {
        Noticia n = new Noticia();
        n.setEtiquetas(Arrays.asList("musica"));
        assertFalse(n.validarEtiquetas());
    }
    
    @Test
    public void testValidarEtiquetasMinimo() {
        Noticia n = new Noticia();
        n.setEtiquetas(Arrays.asList("#test"));
        assertTrue(n.validarEtiquetas());
    }
    
    @Test
    public void testValidarEtiquetasMaximo() {
        Noticia n = new Noticia();
        n.setEtiquetas(Arrays.asList("#uno", "#dos", "#tres", "#cuatro", "#cinco", "#seis"));
        assertTrue(n.validarEtiquetas());
    }
    
    @Test
    public void testValidarEtiquetasExcedeMaximo() {
        Noticia n = new Noticia();
        n.setEtiquetas(Arrays.asList("#uno", "#dos", "#tres", "#cuatro", "#cinco", "#seis", "#siete"));
        assertFalse(n.validarEtiquetas());
    }
    
    @Test
    public void testValidarSinEtiquetas() {
        Noticia n = new Noticia();
        n.setEtiquetas(Arrays.asList());
        assertFalse(n.validarEtiquetas());
    }
    
    @Test
    public void testErroresDeValidacion() {
        Noticia n = new Noticia();
        List<String> errores = n.validar();
        assertFalse(errores.isEmpty());
        assertTrue(errores.size() >= 5);
    }
    
    @Test
    public void testGettersSetters() {
        Noticia n = new Noticia();
        n.setFecha("01/01/2026");
        n.setNivel("media");
        n.setDescripcionCorta("Descripcioncorta");
        n.setDescripcionLarga("Descripcionlarga de20 caracteres");
        n.setEtiquetas(Arrays.asList("#test"));
        
        assertEquals("01/01/2026", n.getFecha());
        assertEquals("media", n.getNivel());
        assertEquals("Descripcioncorta", n.getDescripcionCorta());
        assertEquals("Descripcionlarga de20 caracteres", n.getDescripcionLarga());
        assertEquals(1, n.getEtiquetas().size());
    }
}
