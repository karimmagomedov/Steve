package com.epam.javacore2019.steve2.dbservice.data.query;

import com.epam.javacore2019.steve2.dbservice.DBApplication;
import com.epam.javacore2019.steve2.dbservice.data.Table;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SELECTQueryProcessor implements QueryProcessor {

    public static final String OP_GROUP = "^(SELECT|DELETE)";
    public static final String FLD_GROUP = "([\\s]+[*a-zA-Z, ]+)";
    public static final String FROM_GROUP = "([\\s]+FROM)";
    public static final String TBL_GROUP = "([\\s]+[a-zA-Z]+)";
    public static final String WHERE_GROUP = "([\\s]+WHERE[a-zA-Z0-9 =><]+)*$";

    @Override
    public QueryResult process(String query) {
        Pattern pattern = Pattern.compile(OP_GROUP + FLD_GROUP + FROM_GROUP + TBL_GROUP + WHERE_GROUP);
        QueryResult queryResult = new QueryResult();
        Matcher matcher = pattern.matcher(query);
        queryResult.status = QueryResult.Status.OK;
        if (matcher.find()) {
            String[] fields = matcher.group(2).split(",");
            String tableName = matcher.group(4);
            String whereString = matcher.group(5);

            Table table = DBApplication.INSTANCE.getTable(tableName.trim());
            if (table != null) {
                List<List<String>> result;
                if (whereString != null) {
                    result = table.collect(fields, whereString);
                } else {
                    result = table.collect(fields);
                }
                queryResult.setMessage("COMPLETED SUCCESSFULLY");
                queryResult.setLoad(collectedResultToString(result));
            } else {
                queryResult.setStatus(QueryResult.Status.FAILURE);
                queryResult.setMessage("TABLE DOES NOT EXISTS");
            }
        } else {
            queryResult.setStatus(QueryResult.Status.FAILURE);
            queryResult.setMessage("INVALID QUERY");
        }

        return queryResult;
    }

    private String collectedResultToString(List<List<String>> collectedResult) {
        String result = "";
        for (List<String> list : collectedResult ) {
            for (String s : list) {
                result += s + ",";
            }
            result = result.substring(0, result.length()-1);
            result += ";";
        }
        return result;
    }

}
