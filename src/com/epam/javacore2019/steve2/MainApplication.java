package com.epam.javacore2019.steve2;

import com.epam.javacore2019.steve2.dbservice.DBApplication;
import com.epam.javacore2019.steve2.dbservice.misc.XMLDocumentHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;

public class MainApplication {

    public static Map<String, Enum> applications = new HashMap<>();

    public static void main(String[] args) {

        try {
            startApplications();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public static void startApplications() {
        try {
            Utils.readXMLDocument("main/config.xml", new XMLDocumentHandler() {
                @Override
                public void handleDocument(Document document) {
                    try {
                        NodeList apps = document.getElementsByTagName("application");
                        for (int i = 0, len = apps.getLength(); i < len; i++) {
                            Element el = (Element) (apps.item(i));
                            Class cls = Class.forName(el.getAttribute("class"));
                            Enum appInstance = Enum.valueOf(cls, "INSTANCE");
                            applications.put(el.getAttribute("name"), appInstance);
                            int port = Integer.valueOf(el.getAttribute("port"));
                            cls.getDeclaredMethod("start", int.class).invoke(appInstance, port);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
