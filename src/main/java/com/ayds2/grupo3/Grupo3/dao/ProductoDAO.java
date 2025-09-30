package com.ayds2.grupo3.Grupo3.dao;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import com.ayds2.grupo3.Grupo3.interfaces.IProductoDao;
import com.ayds2.grupo3.Grupo3.models.Producto;
import com.ayds2.grupo3.Grupo3.util.Sql2oDAO;

@Repository
public class ProductoDAO implements IProductoDao {
    @Override
    public Producto getPorId(int id) {
        String sql = "Select * from productos where id = :id;";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Producto.class);
        }
    }
}