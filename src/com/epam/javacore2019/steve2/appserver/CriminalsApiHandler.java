package com.epam.javacore2019.steve2.appserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class CriminalsApiHandler implements HttpHandler  {

    @Override
    public void handle(HttpExchange httpExchange) {
        try {
            String filterParams = httpExchange.getRequestURI().getQuery();
            String whereString = null;
            if (filterParams != null) {
                whereString = buildWhereString(filterParams);
            }
            String dbResult = requestDB(whereString);
            httpExchange.getResponseHeaders().put("Content-Type", Arrays.asList(new String[]{"text/plain"}));
            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            os.write(dbResult.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpExchange.close();
        }
    }

    private String buildWhereString(String urlQuery) {
        urlQuery = urlQuery.replaceAll("&", " AND ");
        urlQuery = urlQuery.replaceAll("!OR!", " OR ");
        urlQuery = urlQuery.replaceAll("!GT!", ">");
        urlQuery = urlQuery.replaceAll("!LT!", "<");
        urlQuery = urlQuery.replaceAll("!LIKE!", " LIKE ");

        return " WHERE " + urlQuery;
    }

    private String requestDB(String whereString) throws Exception {
        URL url = new URL("http://localhost:6701/api/query");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        OutputStream os = connection.getOutputStream();
        String query =
            "SELECT " +
                "id," +
                "firstName," +
                "lastName," +
                "nickname," +
                "criminalFamilyId," +
                "dateOfBirth," +
                "deceased," +
                "dateOfDeath," +
                "numberOfCrimes" +
            " FROM Criminals";
        if (whereString != null) {
            query += whereString;
        }
        os.write(query.getBytes());
        os.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String dbResult = "";
        String line;
        while ((line = br.readLine()) != null) {
            dbResult += line;
        }
        br.close();
        return dbResult;
    }
}
