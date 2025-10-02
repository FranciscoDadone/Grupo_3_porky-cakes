package com.ayds2.grupo3.Grupo3.dao;

import java.sql.Timestamp;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import com.ayds2.grupo3.Grupo3.models.Carrito;
import com.ayds2.grupo3.Grupo3.util.Sql2oDAO;

@Repository
public class CarritoDAO {

    public void insertarProducto(int idCarrito, int idProducto, int cantidad) {
        String sql = "INSERT INTO productos_x_carrito (productoId, carritoId, cantidad) VALUES (:productoId, :carritoId, :cantidad);";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            con.createQuery(sql)
                    .addParameter("productoId", idProducto)
                    .addParameter("carritoId", idCarrito)
                    .addParameter("cantidad", cantidad)
                    .executeUpdate();
        }
    }

    public void actualizarCantidadProducto(int idCarrito, int idProducto, int cantidad) {
        String sql = "UPDATE productos_x_carrito SET cantidad = :cantidad WHERE productoId = :productoId AND carritoId = :carritoId;";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            con.createQuery(sql)
                    .addParameter("productoId", idProducto)
                    .addParameter("carritoId", idCarrito)
                    .addParameter("cantidad", cantidad)
                    .executeUpdate();
        }
    }

    public Integer cantidadDeProductoEnCarrito(int idCarrito, int idProducto) {
        String sql = "SELECT cantidad FROM productos_x_carrito WHERE productoId = :productoId AND carritoId = :carritoId;";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            Integer cantidad = con.createQuery(sql)
                    .addParameter("productoId", idProducto)
                    .addParameter("carritoId", idCarrito)
                    .executeAndFetchFirst(Integer.class);
            return cantidad;
        }
    }

    public Carrito getCarritoCliente(int clienteId) {
        String sql = "SELECT * FROM carritos WHERE clienteId = :clienteId AND comprado = 0;";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            return con.createQuery(sql)
                    .addParameter("clienteId", clienteId)
                    .executeAndFetchFirst(Carrito.class);
        }
    }

    public Carrito crearCarrito(int clienteId) {
        String sql = "INSERT INTO carritos (clienteId, comprado, timestamp) VALUES (:clienteId, 0, :timestamp);";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            Object key = con.createQuery(sql, true)
                    .addParameter("clienteId", clienteId)
                    .addParameter("timestamp", now)
                    .executeUpdate()
                    .getKey();
            int id = ((Number) key).intValue();
            return new Carrito(id, clienteId, false, now);
        }
    }
}