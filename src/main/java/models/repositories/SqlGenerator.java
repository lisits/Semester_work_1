package models.repositories;

import models.entities.ColumnName;
import models.entities.TableName;

import java.lang.reflect.Field;
import java.sql.Time;
import java.util.Date;

public class SqlGenerator {
    public static <T> String createTable(Class<T> entityClass) {
        String str;
        str = "CREATE TABLE IF NOT EXISTS " + entityClass.getDeclaredAnnotation(TableName.class).nameOfTable() + "" +
                "(";
        Field[] fields = entityClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            str = str + stringBuilder(fields[i]);
            if (fields[i].getDeclaredAnnotation(ColumnName.class).notNull()) {
                str = str + " NOT NULL";
            }
            if (i != fields.length - 1) {
                str = str + ",";
            }
        }
        str = str + ");";
        return str;
    }

    public static String findAll(String table, String column, String order, String columnForFilter, String argumentOfFilter) {
        String str = "SELECT * FROM " + table + "";
        if (columnForFilter != null && argumentOfFilter != null && columnForFilter != "" && argumentOfFilter != "") {
            if (columnForFilter.equals("cost")) {
                str = str + " WHERE " + columnForFilter + "=" + argumentOfFilter + "";
            } else {
                str = str + " WHERE " + columnForFilter + " ilike \'%" + argumentOfFilter + "%\'";
            }
        }
        if (column != null && column != "" && order != null && order != "") {
            str = str + " order by " + column + " " + order;
        }
        return str;
    }


    public static String insert(Object entity) {
        Class<?> user = entity.getClass();
        String str;
        str = "INSERT INTO " + user.getDeclaredAnnotation(TableName.class).nameOfTable() + " (";
        Field[] fields = user.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (!fields[i].getDeclaredAnnotation(ColumnName.class).name().equals("id")) {
                if (i != fields.length - 1) {
                    str = str + "" + fields[i].getDeclaredAnnotation(ColumnName.class).name() + ", ";
                } else {
                    str = str + "" + fields[i].getDeclaredAnnotation(ColumnName.class).name() + ") VALUES (";
                }
            }
        }
        try {
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                if (!fields[i].getDeclaredAnnotation(ColumnName.class).name().equals("id")) {
                    if (i != fields.length - 1) {
                        if (fields[i].getType().equals(String.class)) {
                            str = str + "\'" + fields[i].get(entity) + "\', ";
                        } else if (fields[i].getType().equals(Date.class) || fields[i].getType().equals(Time.class)) {
                            str = str + "now() , ";
                        } else {
                            str = str + "" + fields[i].get(entity) + ", ";
                        }
                    } else {
                        if (fields[i].getType().equals(String.class)) {
                            str = str + "\'" + fields[i].get(entity) + "\')";
                        } else if (fields[i].getType().equals(Date.class) || fields[i].getType().equals(Time.class)) {
                            str = str + "now() )";
                        } else {
                            str = str + "" + fields[i].get(entity) + ")";
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        str = str + " RETURNING id";
        return str;
    }

    public static String findById(Object entity) {
        try {
            String str;
            str = "SELECT * FROM " + entity.getClass().getDeclaredAnnotation(TableName.class).nameOfTable() + " WHERE ";
            Field[] fields = entity.getClass().getDeclaredFields();
            fields[0].setAccessible(true);
            str = str + fields[0].getDeclaredAnnotation(ColumnName.class).name() + " =" + fields[0].get(entity) + " ";
            return str;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    public static String update(Object entity) {
        String updatestr;
        Field[] fields = entity.getClass().getDeclaredFields();
        updatestr = "UPDATE " + entity.getClass().getDeclaredAnnotation(TableName.class).nameOfTable() + " SET ";
        try {
            for (int i = 1; i < fields.length; i++) {
                fields[i].setAccessible(true);
                updatestr = updatestr + "" + fields[i].getDeclaredAnnotation(ColumnName.class).name() + " = ";
                if (i != fields.length - 1) {
                    if (fields[i].getType().equals(String.class) || fields[i].getType().equals(Date.class)) {
                        updatestr = updatestr + "\'" + fields[i].get(entity) + "\', ";
                    } else {
                        updatestr = updatestr + "" + fields[i].get(entity) + ", ";
                    }
                } else {
                    if (fields[i].getType().equals(String.class)) {
                        updatestr = updatestr + "\'" + fields[i].get(entity) + "\' WHERE";
                    } else {
                        updatestr = updatestr + "" + fields[i].get(entity) + " WHERE";
                    }
                }
            }
            fields[0].setAccessible(true);
            updatestr = updatestr + " " + fields[0].getDeclaredAnnotation(ColumnName.class).name() + " = " + fields[0].get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return updatestr;
    }


    public static String delete(Object entity) {
        String deletestr;
        try {
            deletestr = "DELETE FROM " + entity.getClass().getDeclaredAnnotation(TableName.class).nameOfTable() +
                    " WHERE " + entity.getClass().getDeclaredFields()[0].getAnnotation(ColumnName.class).name() +
                    " = " + entity.getClass().getDeclaredFields()[0].get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return deletestr;
    }

    public static String deleteById(Long id, String table) {
        String deletestr;
        deletestr = "DELETE FROM " + table + " WHERE id=" + id;
        return deletestr;
    }


    private static String stringBuilder(Field field) {
        String column;
        ColumnName columnName = field.getDeclaredAnnotation(ColumnName.class);
        column = "" + columnName.name() + "";

        if (columnName.identity()) {
            column = column + " bigserial";
            if (columnName.primary()) {
                column = column + " primary key";
            }
        } else if (field.getType().equals(Long.class)) {
            column = column + " bigint";
            if (columnName.primary()) {
                column = column + " primary key";
            }

        } else if (field.getType().equals(Integer.class)) {
            column = column + " INT";
            if (columnName.primary() && columnName.identity()) {
                column = column + " primary key";
            }
            if (columnName.uniq()) {
                column = column + " UNIQUE";
            }
        } else if (field.getType().equals(String.class)) {
            column = column + " varchar";
            if (columnName.uniq()) {
                column = column + " UNIQUE";
            }
        } else if (field.getType().equals(Boolean.TYPE)) {
            if (field.getDeclaredAnnotation(ColumnName.class).defaultBooleanFalse()) {
                column = column + " BOOLEAN DEFAULT false";
            } else {
                column = column + " BOOLEAN";
            }
        } else if (field.getType().equals(Date.class)) {
            column = column + " date";
        } else if (field.getType().equals(Time.class)) {
            column = column + " time";
        }
        return column;

    }
}
