package com.university.news.xml;

import com.university.news.model.Noticia;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.List;

public class XMLCoder {
    
    public static String encode(Noticia noticia) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();
        
        Document document = implementation.createDocument(null, "noticia", null);
        document.setXmlVersion("1.0");
        
        Element root = document.getDocumentElement();
        
        Element fecha = document.createElement("fecha");
        fecha.appendChild(document.createTextNode(noticia.getFecha()));
        root.appendChild(fecha);
        
        Element nivel = document.createElement("nivel");
        nivel.appendChild(document.createTextNode(noticia.getNivel()));
        root.appendChild(nivel);
        
        Element descCorta = document.createElement("descripcionCorta");
        descCorta.appendChild(document.createTextNode(noticia.getDescripcionCorta()));
        root.appendChild(descCorta);
        
        Element descLarga = document.createElement("descripcionLarga");
        descLarga.appendChild(document.createTextNode(noticia.getDescripcionLarga()));
        root.appendChild(descLarga);
        
        Element etiquetas = document.createElement("etiquetas");
        List<String> tags = noticia.getEtiquetas();
        if (tags != null) {
            for (String tag : tags) {
                Element etiqueta = document.createElement("etiqueta");
                etiqueta.appendChild(document.createTextNode(tag));
                etiquetas.appendChild(etiqueta);
            }
        }
        root.appendChild(etiquetas);
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        
        StringWriter writer = new StringWriter();
        Result result = new StreamResult(writer);
        Source source = new DOMSource(document);
        transformer.transform(source, result);
        
        return writer.toString();
    }
}
