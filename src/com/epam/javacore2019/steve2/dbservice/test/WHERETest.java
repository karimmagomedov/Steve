package com.epam.javacore2019.steve2.dbservice.test;

import com.epam.javacore2019.steve2.dbservice.data.TableColumn;
import com.epam.javacore2019.steve2.dbservice.data.TableRow;
import com.epam.javacore2019.steve2.dbservice.data.query.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WHERETest {

    public static final String VALID_STRING_1 = "WHERE id = 5";
    public static final String VALID_STRING_2 = "   WHERE    id = 1 AND name= Tony   ";
    public static final String INVALID_STRING_2 = " WHERE ID = 1 AND";
    public static final String VALID_STRING_3 = " WHERE id = 1 AND name =Tony OR field>5  ";
    public static final String INVALID_STRING_1 = "BAD WHERE";
    public static final String REAL_QUERY =
            "WHERE firstName = Tony " +
            "AND lastName = Soprano " +
            "OR nickname = Stepfather  ";

    public static final TableColumn[] columns = new TableColumn[]{
            new TableColumn("firstName"),
            new TableColumn("lastName"),
            new TableColumn("nickname"),
            new TableColumn("numberOfCrimes")
    };

    public static final TableRow[] rows = new TableRow[]{
            new TableRow().setValues(new String[]{"Enrico", "Soprano", null, "600"}),
            new TableRow().setValues(new String[]{"Tony", "Soprando", null, "200"}),
            new TableRow().setValues(new String[]{"Tony", "Fernandes", null, "400"}),
            new TableRow().setValues(new String[]{"Tony", "Soprano", "Butcher", "100"}),
            new TableRow().setValues(new String[]{"Antony", "Soprando", "Stepfather", "250"})
    };

    static WHEREParser parser = new WHEREParser();

    @Test
    public static void testValidString() {
        assert parser.validate(VALID_STRING_1) : VALID_STRING_1;
        assert parser.validate(VALID_STRING_3) : VALID_STRING_3;
    }

    @Test(enabled = true)
    public static void testInvalidString() {
        assert !parser.validate(INVALID_STRING_1) : INVALID_STRING_1;
    }

    @Test
    public static void testBeautifyValid() {
        assert parser.beautify(VALID_STRING_2).equals("WHERE id = 1 AND name= Tony");
    }

    @Test
    public static void testExtractClause() {
        assert parser.extractClause(VALID_STRING_1).equals("id = 5");
        assert parser.extractClause(VALID_STRING_2).equals("id = 1 AND name= Tony   ");
    }

    @Test
    public static void testRealClause() {
        Map<String, String> mappedValues = new HashMap<>();

        for (TableRow row : rows) {
            List<String> rowValues = row.getValues();
            for (int i = 0, len = rowValues.size(); i < len; i++) {
                mappedValues.put(columns[i].getName(), rowValues.get(i));
            }
            boolean matches = parser.evaluate(mappedValues, REAL_QUERY);
            System.out.printf("\n %s %s", row, String.valueOf(matches));
        }
    }

    @Test
    public static void testBooleanOperatorParse() {
        String op1 = " =  ";
        String op2 = " AND";
        String op3 = "OR";
        String op4 = "XOR";
        assert BooleanOperator.parse(op1).equals("=");
        assert BooleanOperator.parse(op2).equals("AND");
        assert BooleanOperator.parse(op3).equals("OR");
    }


    @Test
    public static void testOperators() {
        OperatorEQ eq = (OperatorEQ)(BooleanOperator.get("="));
        OperatorAND and =(OperatorAND) (BooleanOperator.get("AND"));
        OperatorOR or = (OperatorOR)(BooleanOperator.get("OR"));
        BooleanOperator nonExcisting = (BooleanOperator)(BooleanOperator.get("XOR"));
        assert eq.operate("1", "1");
        assert !eq.operate("1", "elephant");
        assert and.operate(true, true, true);
        assert !and.operate(true, false, false);
        assert or.operate(true, false, true);
        assert !or.operate(false, false, false);
        assert or.operate(true, true, true);
        assert or.operate(false, true, false);
        assert nonExcisting == null;
    }

    @Test
    public static void testEvaluateStatement() {
        String statement1 = " 1 = 2 ";
        String statement2 = " 3 = 3";
        String statement3 = " vasia=vasia ";
        String statement4 = " petia =vasia";
        assert !parser.evaluateStatement(statement1) : statement1;
        assert parser.evaluateStatement(statement2) : statement2;
        assert parser.evaluateStatement(statement3) : statement3;
        assert !parser.evaluateStatement(statement4) : statement4;
    }

    public static void notTest() {
        System.out.println("NOT A TEST METHOD!");
    }

}
