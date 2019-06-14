package com.epam.javacore2019.steve2.dbservice.data.query;


public class OperatorEQ implements BooleanOperator {

    @Override
    public boolean operate(Object... operands) {
        return operands[0].equals(operands[1]);
    }

}