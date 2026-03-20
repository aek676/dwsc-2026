package com.university.news.xml;

import com.university.news.model.Noticia;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class XMLDecoder {
    
    public static Noticia decode(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xml)));
        document.getDocumentElement().normalize();
        
        Noticia noticia = new Noticia();
        
        Element root = document.getDocumentElement();
        
        if (root.getElementsByTagName("fecha").getLength() > 0) {
            noticia.setFecha(root.getElementsByTagName("fecha").item(0).getTextContent());
        }
        
        if (root.getElementsByTagName("nivel").getLength() > 0) {
            noticia.setNivel(root.getElementsByTagName("nivel").item(0).getTextContent());
        }
        
        if (root.getElementsByTagName("descripcionCorta").getLength() > 0) {
            noticia.setDescripcionCorta(root.getElementsByTagName("descripcionCorta").item(0).getTextContent());
        }
        
        if (root.getElementsByTagName("descripcionLarga").getLength() > 0) {
            noticia.setDescripcionLarga(root.getElementsByTagName("descripcionLarga").item(0).getTextContent());
        }
        
        List<String> etiquetas = new ArrayList<>();
        NodeList tagList = root.getElementsByTagName("etiqueta");
        for (int i = 0; i < tagList.getLength(); i++) {
            etiquetas.add(tagList.item(i).getTextContent());
        }
        noticia.setEtiquetas(etiquetas);
        
        return noticia;
    }
}
