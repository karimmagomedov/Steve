package com.epam.javacore2019.steve2.appserver;

import com.epam.javacore2019.steve2.webservice.*;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public enum ServerApplication  {
    INSTANCE;

    public static int PORT;
    public static final String APP_NAME = "Application Service";

    public void start(int port) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 10 );
        PORT = port;
        server.createContext("/api/criminals", new CriminalsApiHandler()); //criminals/{id}
        server.start();
        String message = String.format("%s is running on port: %d", APP_NAME, server.getAddress().getPort());
        System.out.println(message);
    }
}
