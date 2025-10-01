package com.ayds2.grupo3.Grupo3.dao;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;

import com.ayds2.grupo3.Grupo3.models.Carrito;
import com.ayds2.grupo3.Grupo3.util.Sql2oDAO;

@Repository
public class CarritoDAO {

    public void agregarProducto(int idCarrito, int idProducto, int cantidad) {
        String sql = "INSERT INTO productos_x_carrito (producto_id, carrito_id, cantidad) VALUES (:producto_id, :carrito_id, :cantidad);";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            con.createQuery(sql)
                    .addParameter("producto_id", idProducto)
                    .addParameter("carrito_id", idCarrito)
                    .addParameter("cantidad", cantidad)
                    .executeUpdate();
        }
    }

    public Carrito getCarritoCliente(int clienteId) {
        String sql = "SELECT * FROM carritos WHERE cliente_id = :cliente_id AND comprado = 0;";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            return con.createQuery(sql)
                    .addParameter("cliente_id", clienteId)
                    .executeAndFetchFirst(Carrito.class);
        }
    }

    public Carrito crearCarrito(int clienteId) {
        String sql = "INSERT INTO carritos (cliente_id, comprado) VALUES (:cliente_id, 0);";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            int id = (int) con.createQuery(sql, true)
                    .addParameter("cliente_id", clienteId)
                    .executeUpdate()
                    .getKey();
            return new Carrito(id, clienteId, false);
        }
    }
}