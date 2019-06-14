package com.epam.javacore2019.steve2.dbservice.data.query;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WHEREParser {

    public static final String CLAUSE = "^([\\s]*WHERE[\\s]+)([a-zA-Z0-9><=! ]+)";
    public static final String LOP = "[\\s]+(AND|OR)[\\s]+"; //logical operator
    public static final String OR = "[\\s]*OR[\\s]*";
    public static final String AND = "[\\s]*AND[\\s]*";
    public static final String STM =
            "([a-zA-Z0-9]+)" +
            "([\\s]*[LIKE<>=!]+[\\s]*)" +
            "([a-zA-Z0-9]+[\\s]*)";
    public static final String LEXP = LOP + STM;
    public static final String WHERE_REGEX = "" +
            "^([\\s]*WHERE[\\s]+)" +
            STM + "((" + LEXP +  ")*|$)";


    public boolean validate(String whereString) {
        return whereString.matches(WHERE_REGEX);
    }

    public String beautify(String whereString) {
        return whereString.trim().replaceAll("[\\s]+", " ");
    }

    public String extractClause(String whereString) {
        Pattern p = Pattern.compile(CLAUSE);
        Matcher m = p.matcher(whereString);
        if (m.find()) {
            return m.group(2);
        }
        return null;
    }

    public boolean evaluate(Map<String, String> values, String whereString) {

        //validate
        if (!validate(whereString)) {
            throw new RuntimeException("INVALID CLAUSE: "  + whereString);
        }

        //extract clause
        String clause = extractClause(whereString);
        clause = beautify(clause);
        if (clause == null) {
            throw new RuntimeException("INVALID CLAUSE: " + whereString);
        }

        for (Map.Entry<String, String> entry : values.entrySet()) {
            String value = entry.getValue();
            if (value != null) {
                clause = clause.replaceAll(entry.getKey(), entry.getValue());
            } else {
                clause = clause.replaceAll(entry.getKey(), "null");
            }
        }

        String[] ORStatements = clause.split(OR);
        Boolean[] ORResults = new Boolean[ORStatements.length];
        int orIndex = 0;

        BooleanOperator operatorOR = BooleanOperator.get("OR");
        BooleanOperator operatorAND = BooleanOperator.get("AND");

        for (String ORStatement : ORStatements) {
            String[] ANDStatements = ORStatement.split(AND);
            Boolean[] ANDResults = new Boolean[ANDStatements.length];
            int andIndex = 0;
            for (String statement : ANDStatements) {
                ANDResults[andIndex] = evaluateStatement(statement);
                andIndex++;
            }
            ORResults[orIndex] = operatorAND.operate(ANDResults);
            orIndex++;
        }
        return operatorOR.operate(ORResults);
    }

    public boolean evaluateStatement(String statement){
        Pattern pattern = Pattern.compile(STM);
        Matcher matcher = pattern.matcher(statement);
        if (!matcher.find()) {
            throw new RuntimeException("CANNOT EVALUATE STATEMENT: " + statement);
        }
        String operand1 = matcher.group(1).trim();
        String operatorSymbol = BooleanOperator.parse(matcher.group(2));
        String operand2 = matcher.group(3).trim();
        BooleanOperator operator = BooleanOperator.get(operatorSymbol);
        return operator.operate(operand1, operand2);
    }
}
