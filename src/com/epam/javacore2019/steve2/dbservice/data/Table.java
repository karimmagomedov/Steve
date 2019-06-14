package com.epam.javacore2019.steve2.dbservice.data;

import com.epam.javacore2019.steve2.dbservice.data.query.WHEREParser;
import com.epam.javacore2019.steve2.dbservice.misc.DataHandler;
import com.epam.javacore2019.steve2.dbservice.misc.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {

    private List<TableRow> rows;
    private TableMetaData metaData;

    public Table(TableMetaData metaData) {
        this.metaData = metaData;
        rows = new ArrayList<>();
    }

    public void load() {
        Utils.readFileLineByLine(metaData.getPathToData(), new DataHandler() {
            @Override
            public void handleString(String line) {
                TableRow row = new TableRow();
                row.setValues(line);
                addRow(row);
                System.out.println(row);
            }
        });
        System.out.println(this);
    }

    public void save() {

    }

    public List<List<String>> collect(String[] fields) {
        List<List<String>> result = new ArrayList<>();
        int[] indexes = new int[fields.length];
        for (int i = 0, len = fields.length; i < len; i++) {
            int index = getFieldIndex(fields[i]);
            if (index != -1) {
                indexes[i] = index;
            }
        }
        for (TableRow row : rows) {
            result.add(collectFieldValues(indexes, row));
        }
        return result;
    }

    public List<List<String>> collect(String[] fields, String whereString) {
        WHEREParser whereParser = new WHEREParser();
        List<List<String>> result = new ArrayList<>();
        int[] indexes = new int[fields.length];
        for (int i = 0, len = fields.length; i < len; i++) {
            int index = getFieldIndex(fields[i]);
            if (index != -1) {
                indexes[i] = index;
            }
        }
        for (TableRow row : rows) {
            boolean matches = whereParser.evaluate(rowToMap(row), whereString);
            if (matches) {
                result.add(collectFieldValues(indexes, row));
            }
        }
        return result;
    }

    public Map<String, String> rowToMap(TableRow row) {
        Map<String, String> map = new HashMap<>();
        List<String> columnNames = metaData.getColumnNames();
        List<String> values = row.getValues();
        int i = 0;
        for (String name : columnNames) {
            map.put(name, values.get(i));
            i++;
        }
        return map;
    }

    public List<String> collectFieldValues(int[] indexes, TableRow row) {
        List<String> result = new ArrayList<>();
        for (int i : indexes) {
            result.add(row.getValues().get(i));
        }
        return result;
    }

    public int getFieldIndex(String fieldName) {
        return metaData.getColumnIndex(fieldName);
    }

    public void addRow(TableRow row) {
        rows.add(row);
    }

    @Override
    public String toString() {
        String result = "\n" + metaData.getTableName();
        result += "\nStructure file: " + metaData.getPathToStructure();
        result += "\nData file: " + metaData.getPathToStructure();
        result += "\n" + metaData.getColumns();
        result += "\nnumber of rows: " + rows.size();
        return result;
    }
}
