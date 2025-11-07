package com.ayds2.grupo3.Grupo3.dao;

import java.lang.reflect.Field;
import java.math.BigInteger;

import org.sql2o.Connection;
import org.sql2o.Query;

import com.ayds2.grupo3.Grupo3.util.Sql2oDAO;

public abstract class CrudDAO<T> {

    /*
     * Esta clase es necesaria para poder implementar el acceso al
     * tipo especifico T.class
     */
    abstract public Class<T> getTClass();

    // Retorna la expresión que accede a la fila por clave primaria
    abstract public String getTablePK();

    // Retorna el nombre de la clase
    abstract public String getTableName();

    public BigInteger insert(T t) {
        Class cls = t.getClass();
        Field[] fields = cls.getDeclaredFields();

        String str1 = "";
        String str2 = "";
        for (Field field : fields) {
            String name = field.getName();
            str1 += name + ",";
            str2 += ":" + name + ",";
        }

        str1 = str1.substring(0, str1.length() - 1);
        str2 = str2.substring(0, str2.length() - 1);

        String sql = "INSERT INTO " + getTableName() + " (" + str1 + ") VALUES (" + str2 + ");";

        System.out.println(sql);

        try (Connection con = Sql2oDAO.getSql2o().open()) {
            Query query = con.createQuery(sql);
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(t);
                    query.addParameter(field.getName(), value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            return (BigInteger) query.bind(t).executeUpdate().getKey();
        }
    }

    public T select(int id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE " + getTablePK() + " = :id;";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(getTClass());
        }
    }

}
