package com.epam.javacore2019.steve2.webservice;

import com.epam.javacore2019.steve2.dbservice.misc.XMLDocumentHandler;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public enum WebClientApplication {
    INSTANCE;

    public static int PORT;
    public static final String APP_NAME = "Web Client Service";
    private HttpServer server;

    public void start(int port) throws Exception {
        server = HttpServer.create(new InetSocketAddress(port), 10 );
        PORT = port;
        initHandlers();
        server.start();
        String message = String.format("%s is running on port: %d", APP_NAME, server.getAddress().getPort());
        System.out.println(message);
    }

    public void initHandlers() {
        final Map<String, String> handlerMap = new HashMap<>();
        try {
            Utils.readXMLDocument("webclient/web.xml", new XMLDocumentHandler() {
                @Override
                public void handleDocument(Document document) {
                    try {
                        NodeList list = document.getElementsByTagName("handler");
                        for (int i =0 , len = list.getLength(); i < len; i++) {
                            Element el = (Element)(list.item(i));
                            handlerMap.put(el.getAttribute("path"), el.getAttribute("class"));
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
        try {
            for (Map.Entry<String, String> entry : handlerMap.entrySet()) {
                server.createContext(entry.getKey(), (HttpHandler)(Class.forName(entry.getValue()).newInstance()));
            }
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (InstantiationException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        }
    }
}
