package com.javacore.steve.db;

import sun.rmi.log.LogInputStream;


import java.io.FileInputStream;
import java.util.*;

public class  Table {

    protected String name;

    protected List<String> columns;

    protected List<Record> records;


    {
        records = new ArrayList<>();
    }

    public Table(String name, List<String> columns){
        this.name = name;
        this.columns = columns;
    }

    public void insert (Record record){
        records.add(record);
    }

    //"SELECT id,firstName,lastName;"

    public void select (List<String> fieldNames){
        Integer[] index = new Integer[fieldNames.size()];
        Iterator fieldName = fieldNames.iterator();
        for (int i=0;i<fieldNames.size();i++){
                index[i] = columns.indexOf(fieldName.next());
        }
        Iterator it = records.iterator();
        List<String> result = new ArrayList<>();
        while (it.hasNext()) {
            Record r = (Record) it.next();
            for (int i = 0; i < fieldNames.size(); i++) {
                result.add(r.values.get(index[i]));
            }
        }
        Thread thread = new Thread(){
            @Override
            public void run(){
                int k = 0;
                for (int j=0;j<index.length;j++) {
                    try{
                        System.out.print("------------------------------" + "\n");
                        for (int i = 0; i < index.length; i++) {
                            if (k<result.size()) {
                                System.out.print(result.get(k) + " ");
                                k++;
                            }
                            Thread.sleep(1000);
                        }
                        System.out.print("\n");
                    } catch (Exception e){
                    }
                }
            }
        };
        thread.start();
    }

    public List<String> getColumns(){
        return columns;
    }



    public List<String> selectField(String fieldName){
        int index = columns.indexOf(fieldName);
        Iterator it = records.iterator();
        List<String> result = new ArrayList<>();
        while (it.hasNext()){
            Record r = (Record)it.next();
            result.add(r.values.get(index));
        }
        return result;
    }




}
