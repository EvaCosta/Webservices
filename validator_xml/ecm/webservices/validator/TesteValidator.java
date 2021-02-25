package ecm.webservices.validator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.EntityResolver2;

public class TesteValidator {
	public final static String ARQUIVO_DTD = "biblioteca.dtd";

	public static void main(String[] args) throws Exception {
		//validadorXSD(new File("smartphone.xml"), new File("smartphone.xsd"));
		
		validadorDTD("biblioteca.xml");
	}
	
	public static Document validadorDTD(String xmlFilePath) throws ParserConfigurationException, SAXException, IOException {
		
	    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	    domFactory.setValidating(true);
	    DocumentBuilder builder = domFactory.newDocumentBuilder();
	    builder.setEntityResolver(new EntityResolver() {
			@SuppressWarnings("resource")
			@Override
			public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
				if (systemId.endsWith(".dtd")) {
		            return new InputSource(new FileInputStream(ARQUIVO_DTD));
		        }
				return null;
			}
		});
	    Document doc = builder.parse(xmlFilePath);
	    return doc;
	}
	
	public static void validadorXSD (File xml, File xsd) throws SAXException, IOException{
		Source schemaFile = new StreamSource(xsd);
		Source xmlFile = new StreamSource(xml);
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = null;
		try {
			schema = schemaFactory.newSchema(schemaFile);
			System.out.println("XML válido!");
		} catch (SAXException e) {
			System.out.println("XML invalido!");
		}
		Validator validator = schema.newValidator();
		validator.validate(xmlFile);
	}

}
