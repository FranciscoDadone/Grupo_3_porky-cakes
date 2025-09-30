package com.ayds2.grupo3.Grupo3.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.sql2o.Sql2o;
import jakarta.annotation.PostConstruct;

@Component
public class Sql2oDAO {
    private static Sql2o sql2o;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @PostConstruct
    public void init() {
        if (sql2o == null) {
            sql2o = new Sql2o(url, username, password);
        }
    }

    public static Sql2o getSql2o() {
        return sql2o;
    }
    
}
