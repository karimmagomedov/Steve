package com.epam.javacore2019.steve2.dbservice.data.query;

import java.util.HashMap;
import java.util.Map;

public interface BooleanOperator {

    Map<String, BooleanOperator> OPERATOR_MAP = new HashMap<String, BooleanOperator>() {
        {
            this.put("=", new OperatorEQ());
            this.put("AND", new OperatorAND());
            this.put("OR", new OperatorOR());
            this.put(">", new OperatorGT());
            this.put("<", new OperatorLT());
            this.put("LIKE", new OperatorLIKE());
        }
    };

    boolean operate(Object... operands);

    static String parse(String rawString) {
        String operatorString = rawString.replaceAll("[\\s]*", "");
        if (!OPERATOR_MAP.containsKey(operatorString)) {
            throw new RuntimeException("\nUNABLE TO PARSE OPERATOR STRING: NO SUCH OPERATOR \"" + rawString + "\"");
        }
        return operatorString;
    };

    static BooleanOperator get(String operatorString) {
        return OPERATOR_MAP.get(operatorString);
    }
}
