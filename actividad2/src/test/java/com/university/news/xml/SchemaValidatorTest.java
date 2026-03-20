package com.university.news.xml;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class SchemaValidatorTest {
    
    private static final String XSD_PATH = "src/main/resources/noticia.xsd";
    
    @Test
    public void testXMLValido() {
        String xml = "<?xml version=\"1.0\"?><noticia>" +
            "<fecha>20/03/2026</fecha>" +
            "<nivel>alta</nivel>" +
            "<descripcionCorta>Noticiaejemplo</descripcionCorta>" +
            "<descripcionLarga>Descripcionlarga de prueba20 caracteres</descripcionLarga>" +
            "<etiquetas><etiqueta>#test</etiqueta></etiquetas>" +
            "</noticia>";
        
        assertTrue(SchemaValidator.validate(xml, XSD_PATH));
    }
    
    @Test
    public void testXMLSinElementosRequeridos() {
        String xml = "<?xml version=\"1.0\"?><noticia>" +
            "<fecha>20/03/2026</fecha>" +
            "</noticia>";
        
        assertFalse(SchemaValidator.validate(xml, XSD_PATH));
        List<String> errores = SchemaValidator.getValidationErrors(xml, XSD_PATH);
        assertFalse(errores.isEmpty());
    }
    
    @Test
    public void testXMLMalFormado() {
        String xml = "no es xml";
        List<String> errores = SchemaValidator.getValidationErrors(xml, XSD_PATH);
        assertFalse(errores.isEmpty());
    }
    
    @Test
    public void testXMLConMasDe6Etiquetas() {
        String xml = "<?xml version=\"1.0\"?><noticia>" +
            "<fecha>20/03/2026</fecha>" +
            "<nivel>alta</nivel>" +
            "<descripcionCorta>Noticiaejemplo</descripcionCorta>" +
            "<descripcionLarga>Descripcionlarga de prueba20 caracteres</descripcionLarga>" +
            "<etiquetas>" +
            "<etiqueta>#uno</etiqueta>" +
            "<etiqueta>#dos</etiqueta>" +
            "<etiqueta>#tres</etiqueta>" +
            "<etiqueta>#cuatro</etiqueta>" +
            "<etiqueta>#cinco</etiqueta>" +
            "<etiqueta>#seis</etiqueta>" +
            "<etiqueta>#siete</etiqueta>" +
            "</etiquetas>" +
            "</noticia>";
        
        assertFalse(SchemaValidator.validate(xml, XSD_PATH));
    }
}
