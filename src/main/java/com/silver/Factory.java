package com.silver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Function;

public class Factory {

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:h2:tcp://localhost/~/test",
                    "sa",
                    "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static <T, R> Function<T, R> getSomething(Function<T, R> function) {
        return function;
    }
}
