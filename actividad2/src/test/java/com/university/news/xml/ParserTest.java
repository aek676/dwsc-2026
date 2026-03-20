package com.university.news.xml;

import org.junit.Test;
import static org.junit.Assert.*;

public class ParserTest {
    
    @Test
    public void testXMLBienFormado() {
        String xml = "<?xml version=\"1.0\"?><noticia><fecha>20/03/2026</fecha></noticia>";
        assertTrue(Parser.isWellFormed(xml));
    }
    
    @Test
    public void testXMLMalFormado() {
        String xml = "<noticia><fecha>20/03/2026</fecha></erroneo>";
        assertFalse(Parser.isWellFormed(xml));
    }
    
    @Test
    public void testXMLVacio() {
        assertFalse(Parser.isWellFormed(""));
    }
    
    @Test
    public void testGetErrorParseo() {
        String xml = "<noticia><fecha>20/03/2026</fecha></erroneo>";
        String error = Parser.getParseError(xml);
        assertNotNull(error);
        assertTrue(error.contains("Error"));
    }
    
    @Test
    public void testGetErrorSinError() {
        String xml = "<?xml version=\"1.0\"?><noticia><fecha>20/03/2026</fecha></noticia>";
        String error = Parser.getParseError(xml);
        assertNull(error);
    }
}
