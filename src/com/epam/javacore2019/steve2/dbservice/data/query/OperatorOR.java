package com.epam.javacore2019.steve2.dbservice.data.query;

public class OperatorOR implements BooleanOperator {
    @Override
    public boolean operate(Object... operands) {
        for (Object operand : operands) {
            if (operand instanceof String) {
                return Boolean.valueOf((String)operand);
            } else if ((Boolean)operand) {
                return true;
            }
        }
        return false;
    }
}
