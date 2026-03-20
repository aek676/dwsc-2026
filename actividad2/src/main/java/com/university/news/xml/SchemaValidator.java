package com.university.news.xml;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class SchemaValidator {
    
    public static class ValidationErrorHandler implements ErrorHandler {
        private List<String> errors = new ArrayList<>();
        
        @Override
        public void warning(SAXParseException exception) {
            errors.add("WARNING: " + exception.getMessage());
        }
        
        @Override
        public void error(SAXParseException exception) {
            errors.add("ERROR: " + exception.getMessage() + " (linea " + exception.getLineNumber() + ")");
        }
        
        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            errors.add("FATAL: " + exception.getMessage() + " (linea " + exception.getLineNumber() + ")");
        }
        
        public List<String> getErrors() {
            return errors;
        }
    }
    
    public static boolean validate(String xmlContent, String xsdPath) {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            
            ValidationErrorHandler errorHandler = new ValidationErrorHandler();
            validator.setErrorHandler(errorHandler);
            
            Source xmlFile = new StreamSource(new StringReader(xmlContent));
            validator.validate(xmlFile);
            
            return errorHandler.getErrors().isEmpty();
        } catch (SAXException | IOException e) {
            return false;
        }
    }
    
    public static List<String> getValidationErrors(String xmlContent, String xsdPath) {
        List<String> errors = new ArrayList<>();
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            
            ValidationErrorHandler errorHandler = new ValidationErrorHandler();
            validator.setErrorHandler(errorHandler);
            
            Source xmlFile = new StreamSource(new StringReader(xmlContent));
            validator.validate(xmlFile);
            
            errors = errorHandler.getErrors();
        } catch (SAXException | IOException e) {
            errors.add(e.getMessage());
        }
        return errors;
    }
}
