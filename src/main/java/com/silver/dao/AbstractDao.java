package com.silver.dao;

import com.silver.orm.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractDao<T> implements GenericDao<T> {

    protected Connection connection;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public T create(T t) {
        String sql = "INSERT INTO users (name, email, password) VALUES ('name', 'email', 'password');";
        return null;
    }

    @Override
    public T findById(Long id) throws Exception{
        Class<T> t = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        final T object = t.newInstance();

        DBTable dbTable = t.getAnnotation(DBTable.class);
        if (dbTable == null) {
            throw new RuntimeException("No DBTable annotations in class " + t.getName());
        }
        String tableName = dbTable.name();

        List<String> idColumns = new ArrayList<>();
        Map<Field, String> annotationTypes = new HashMap<>();
        for (Field field : t.getDeclaredFields()) {
            String columnId = null;
            //TODO get rid of instanceof
            Annotation[] annotations = field.getDeclaredAnnotations();
            if (annotations.length < 1)
                continue; // Not a db table column
            if (annotations[0] instanceof SQLLong) {
                SQLLong sqlLong = (SQLLong) annotations[0];
                if (sqlLong.constraints().primaryKey()) {
                    columnId = sqlLong.column();
                    idColumns.add(columnId);
                }
                annotationTypes.put(field, "SQLLong");

            } else if (annotations[0] instanceof SQLInteger) {
                annotationTypes.put(field, "SQLInteger");
            } else if (annotations[0] instanceof SQLString) {
                annotationTypes.put(field, "SQLString");
            }
        }
        if (idColumns.size() > 1) throw new RuntimeException("Specify only one field as primary key");
        PreparedStatement statement = null;

        String sql = "SELECT * FROM " + tableName + " WHERE " + idColumns.get(0) + " = ?";
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            connection.commit();

            while (resultSet.next()) {
                annotationTypes.forEach((field, s) -> {
                    if (s.equals("SQLString")) {
                        SQLString sqlString = (SQLString) field.getAnnotations()[0];
                        String value = null;
                        try {
                            value = resultSet.getString(sqlString.column());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        field.setAccessible(true);
                        try {
                            field.set(object, value);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    } else if (s.equals("SQLLong")) {
                        SQLLong sqlLong = (SQLLong) field.getAnnotations()[0];
                        Long value = null;
                        try {
                            value = resultSet.getLong(sqlLong.column());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        field.setAccessible(true);
                        try {
                            field.set(object, value);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    } else if (s.equals("SQLInteger")) {
                        SQLInteger sqlInteger = (SQLInteger) field.getAnnotations()[0];
                        Integer value = 0;
                        try {
                            value = resultSet.getInt(sqlInteger.column());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        field.setAccessible(true);
                        try {
                            field.set(object, value);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    connection.rollback();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return object;
    }

    @Override
    public T update(T t) {
        String sql = "UPDATE users SET name = 'Name', email = 'email', password = 'password' WHERE userId = 2;";
        return null;
    }

    @Override
    public T delete(T t) {
        String sql = "DELETE FROM users WHERE userId = 2;";
        return null;
    }

    private String getConstraints(Constraints con) {
        String constraints = "";
        if (!con.allowNull())
            constraints += " NOT NULL";
        if (con.primaryKey())
            constraints += " PRIMARY KEY";
        if (con.unique())
            constraints += " UNIQUE";
        return constraints;
    }
}
