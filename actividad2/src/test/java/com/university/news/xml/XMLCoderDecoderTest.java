package com.university.news.xml;

import com.university.news.model.Noticia;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class XMLCoderDecoderTest {
    
    @Test
    public void testEncodeDecode() throws Exception {
        Noticia original = new Noticia(
            "20/03/2026",
            "alta",
            "Noticiaejemplo",
            "Descripcionlarga de prueba con20 caracteres",
            Arrays.asList("#musica", "#festivalAlmeria")
        );
        
        String xml = XMLCoder.encode(original);
        assertNotNull(xml);
        assertTrue(xml.contains("<noticia>"));
        assertTrue(xml.contains("<fecha>20/03/2026</fecha>"));
        assertTrue(xml.contains("<nivel>alta</nivel>"));
        assertTrue(xml.contains("<descripcionCorta>Noticiaejemplo</descripcionCorta>"));
        assertTrue(xml.contains("<etiqueta>#musica</etiqueta>"));
        assertTrue(xml.contains("<etiqueta>#festivalAlmeria</etiqueta>"));
        
        Noticia decodificada = XMLDecoder.decode(xml);
        assertEquals(original.getFecha(), decodificada.getFecha());
        assertEquals(original.getNivel(), decodificada.getNivel());
        assertEquals(original.getDescripcionCorta(), decodificada.getDescripcionCorta());
        assertEquals(original.getDescripcionLarga(), decodificada.getDescripcionLarga());
        assertEquals(original.getEtiquetas(), decodificada.getEtiquetas());
    }
    
    @Test
    public void testEncodeConEtiquetasVarias() throws Exception {
        Noticia n = new Noticia(
            "15/05/2026",
            "media",
            "Festivaldevideojuegos",
            "Descripcionlarga del festivaldevideojuegos para todos",
            Arrays.asList("#gaming", "#almeria", "#ocio")
        );
        
        String xml = XMLCoder.encode(n);
        Noticia decodificada = XMLDecoder.decode(xml);
        
        assertEquals(3, decodificada.getEtiquetas().size());
        assertTrue(decodificada.getEtiquetas().contains("#gaming"));
    }
    
    @Test(expected = Exception.class)
    public void testDecodeXMLInvalido() throws Exception {
        XMLDecoder.decode("no es xml valido");
    }
}
