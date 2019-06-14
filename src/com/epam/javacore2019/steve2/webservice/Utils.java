package com.epam.javacore2019.steve2.webservice;

import com.epam.javacore2019.steve2.dbservice.misc.XMLDocumentHandler;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Utils {

    public static byte[] readBytes(String fileName) {
        byte[] result = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileName);
            result = new byte[fis.available()];
            fis.read(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static void readXMLDocument(String fileName, XMLDocumentHandler handler) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        File file = new File(fileName);
        Document doc = builder.parse(file);
        handler.handleDocument(doc);
    }
}
