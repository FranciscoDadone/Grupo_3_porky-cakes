package com.ayds2.grupo3.Grupo3.dao;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import com.ayds2.grupo3.Grupo3.models.Producto;
import com.ayds2.grupo3.Grupo3.util.Sql2oDAO;

@Repository
public class ProductoDAO {
    public Producto getPorId(int productoId) {
        String sql = "Select * from productos where id = :id;";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            return con.createQuery(sql)
                    .addParameter("id", productoId)
                    .executeAndFetchFirst(Producto.class);
        }
    }

    public void actualizarStock(int productoId, int nuevoStock) {
        String sql = "UPDATE productos SET stock = :stock WHERE id = :id;";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            con.createQuery(sql)
                    .addParameter("id", productoId)
                    .addParameter("stock", nuevoStock)
                    .executeUpdate();
        }
    }
}