package com.epam.javacore2019.steve2.dbservice.data.query;

import java.io.InputStream;
import java.security.InvalidParameterException;

public class OperatorGT implements BooleanOperator {
    @Override
    public boolean operate(Object... operands) {
        //try integer
        try {
            if (operands[0]  instanceof String && operands[1] instanceof String) {
                Integer i1 = Integer.valueOf((String)(operands[0]));
                Integer i2 = Integer.valueOf((String)(operands[1]));
                return i1 > i2;
            }
        } catch (NumberFormatException e) {
            //Not integer, do nothing
        }

        if (operands[0] instanceof Comparable && operands[1] instanceof Comparable) {
            int result = ((Comparable)(operands[0])).compareTo((Comparable)(operands[1]));
            return result > 0 ? true : false;
        }

        throw new RuntimeException("INCOMPARABLE OPERANDS: " + operands);
    }
}
