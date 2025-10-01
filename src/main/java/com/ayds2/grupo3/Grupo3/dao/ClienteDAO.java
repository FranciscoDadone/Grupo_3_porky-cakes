package com.ayds2.grupo3.Grupo3.dao;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import com.ayds2.grupo3.Grupo3.models.Cliente;
import com.ayds2.grupo3.Grupo3.util.Sql2oDAO;

@Repository
public class ClienteDAO {
    public Cliente getPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = :id;";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Cliente.class);
        }
    }
}
