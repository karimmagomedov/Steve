package com.epam.javacore2019.steve2.dbservice;

import com.epam.javacore2019.steve2.dbservice.data.Table;
import com.epam.javacore2019.steve2.dbservice.data.query.QueryResult;
import com.epam.javacore2019.steve2.dbservice.dbstate.DBState;
import com.epam.javacore2019.steve2.dbservice.dbstate.DBStateInit;
import com.epam.javacore2019.steve2.dbservice.dbstate.DBStateRunning;
import com.epam.javacore2019.steve2.dbservice.dbstate.DBStateStop;
import com.epam.javacore2019.steve2.dbservice.server.DBServer;
import com.epam.javacore2019.steve2.dbservice.test.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public enum DBApplication {
    INSTANCE;

    private Map<String, Table> tables = new HashMap<>();

    public static final String DATA_ENCRYPTION_LEVEL = "LOW";
    private DBState currentState;
    public DBState stateInit = new DBStateInit("Initializing");
    public DBState stateRun = new DBStateRunning("Running");
    public DBState stateStop = new DBStateStop("Shutting Down");

    public void start(int port) {
        DBServer.PORT = port;
        boolean testEnabled = Boolean.valueOf(System.getProperty("et"));
        if (testEnabled) {
            try {
                runTests("com.epam.javacore2019.steve2.dbservice.test.WHERETest");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        changeState(stateInit);
    }

    @Deprecated
    public void stop() {
        currentState.onStop();
    }

    public QueryResult query(String query) {
        return currentState.onQuery(query);
    }

    public String getStateName() {
        return currentState.getName();
    }

    public void changeState(DBState state) {
        if (currentState != null) {
            if (currentState.equals(state)) {
                return;
            } else {
                currentState.exit();
            }
        }
        currentState = state;
        currentState.enter();
    }

    public void addTable(String tableName, Table table) {
        tables.put(tableName, table);
    }

    public Table getTable(String tableName) {
        return tables.get(tableName);
    }

    private void runTests(String className) throws Exception {
        int passed = 0, failed = 0;
        for (Method m : Class.forName(className).getMethods()) {
            Test testAnnotation = m.getAnnotation(Test.class);
            if (testAnnotation != null && testAnnotation.enabled()) {
                try {
                    m.invoke(null);
                    System.out.printf("\nTest %s PASSED ", m.getName());
                    passed++;
                } catch (Throwable ex) {
                    System.out.printf("\nTest %s FAILED: %s ", m.getName(), ex.getCause());
                    failed++;
                }
            }
        }
        System.out.printf("Passed: %d, Failed %d%n", passed, failed);
    }
}
