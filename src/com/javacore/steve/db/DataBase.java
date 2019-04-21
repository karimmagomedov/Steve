package com.javacore.steve.db;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataBase {

    Map<String, Table> table;

    private Object tableLock = new Object();

    //DB.query ("SELECT ID,NAME,EMAIL FROM CRIMINALS WHERE ID=?");
    public List<Record> select(){
        System.out.println("Starting fetching records...");
        synchronized (tableLock){
        try {
            Thread.sleep(500);
            System.out.println("Finishing fetching records...");
        }catch (InterruptedException e){
        }
        }

        return null;
    }

    public void update(){
        System.out.println("Starting updating database...");
        synchronized (tableLock){
        try {
            Thread.sleep(10000);
            System.out.println("Finishing updating database...");
        } catch (InterruptedException e){

            }
        }
    }

    public static List<String[]> readDataFile (String fileName){
        FileInputStream fis = null;
        List<String[]> result = new ArrayList<>();
        BufferedReader br;
        try {
            fis = new FileInputStream(fileName);
            br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = br.readLine())!=null){
                System.out.println("Source Line: "+line);
                result.add(line.split(";"));
            }
        } catch (FileNotFoundException nffe){

        }catch (IOException icex){

        } finally {
            try{
                fis.close();
            } catch (IOException ice2){

            }
        }
        return result;
    }

    public void delete(){

    }

    public void insert (Record record, Table table){

    }
}
