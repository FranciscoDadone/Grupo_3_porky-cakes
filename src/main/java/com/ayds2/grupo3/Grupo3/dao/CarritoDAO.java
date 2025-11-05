package com.ayds2.grupo3.Grupo3.dao;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;

import com.ayds2.grupo3.Grupo3.enums.EstadoPago;
import com.ayds2.grupo3.Grupo3.models.Carrito;
import com.ayds2.grupo3.Grupo3.models.Producto;
import com.ayds2.grupo3.Grupo3.util.Sql2oDAO;

@Repository
public class CarritoDAO {

    public void insertarProducto(int carritoId, int productoId, int cantidad) {
        String sql = "INSERT INTO productos_x_carrito (productoId, carritoId, cantidad) VALUES (:productoId, :carritoId, :cantidad);";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            con.createQuery(sql)
                    .addParameter("productoId", productoId)
                    .addParameter("carritoId", carritoId)
                    .addParameter("cantidad", cantidad)
                    .executeUpdate();
        }
    }

    public void actualizarCantidadProducto(int carritoId, int productoId, int cantidad) {
        String sql = "UPDATE productos_x_carrito SET cantidad = :cantidad WHERE productoId = :productoId AND carritoId = :carritoId;";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            con.createQuery(sql)
                    .addParameter("productoId", productoId)
                    .addParameter("carritoId", carritoId)
                    .addParameter("cantidad", cantidad)
                    .executeUpdate();
        }
    }

    public Integer cantidadDeProductoEnCarrito(int carritoId, int productoId) {
        String sql = "SELECT cantidad FROM productos_x_carrito WHERE productoId = :productoId AND carritoId = :carritoId;";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            Integer cantidad = con.createQuery(sql)
                    .addParameter("productoId", productoId)
                    .addParameter("carritoId", carritoId)
                    .executeAndFetchFirst(Integer.class);
            return cantidad;
        }
    }

    public Carrito getCarritoCliente(int clienteId) {
        String sql = "SELECT * FROM carritos WHERE clienteId = :clienteId AND fechaCompra IS NULL;";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            return con.createQuery(sql)
                    .addParameter("clienteId", clienteId)
                    .executeAndFetchFirst(Carrito.class);
        }
    }

    public Carrito getCarritoPorId(int carritoId) {
        String sql = "SELECT * FROM carritos WHERE id = :carritoId;";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            return con.createQuery(sql)
                    .addParameter("carritoId", carritoId)
                    .executeAndFetchFirst(Carrito.class);
        }
    }

    public Carrito crearCarrito(int clienteId) {
        String sql = "INSERT INTO carritos (clienteId, fechaCompra, estadoPago) VALUES (:clienteId, NULL, 'PENDIENTE');";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            Object key = con.createQuery(sql, true)
                    .addParameter("clienteId", clienteId)
                    .executeUpdate()
                    .getKey();
            int id = ((Number) key).intValue();
            return new Carrito(id, clienteId, null, null, null, null, EstadoPago.PENDIENTE);
        }
    }

    public Producto[] getProductosDelCarrito(int carritoId) {
        String sql = "SELECT p.id, p.nombre, p.descripcion, p.precio, p.stock " +
                     "FROM productos p " +
                     "JOIN productos_x_carrito pc ON p.id = pc.productoId " +
                     "WHERE pc.carritoId = :carritoId;";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            return con.createQuery(sql)
                    .addParameter("carritoId", carritoId)
                    .executeAndFetch(Producto.class)
                    .toArray(new Producto[0]);
        }
    }

    public double calcularTotalCarrito(int carritoId) {
        String sql = "SELECT SUM(p.precio * pc.cantidad) AS total " +
                     "FROM productos p " +
                     "JOIN productos_x_carrito pc ON p.id = pc.productoId " +
                     "WHERE pc.carritoId = :carritoId;";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            Double total = con.createQuery(sql)
                    .addParameter("carritoId", carritoId)
                    .executeAndFetchFirst(Double.class);
            return total != null ? total : 0.0;
        }
    }

    public void actualizarExternalReferenceMp(int carritoId, String externalReferenceMp) {
        String sql = "UPDATE carritos SET externalReferenceMp = :externalReferenceMp WHERE id = :carritoId;";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            con.createQuery(sql)
                    .addParameter("externalReferenceMp", externalReferenceMp)
                    .addParameter("carritoId", carritoId)
                    .executeUpdate();
        }
    }

    public void actualizarEnvioId(int carritoId, int envioId) {
        String sql = "UPDATE carritos SET envioId = :envioId WHERE id = :carritoId;";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            con.createQuery(sql)
                    .addParameter("envioId", envioId)
                    .addParameter("carritoId", carritoId)
                    .executeUpdate();
        }
    }

    public void actualizarEstadoPago(int carritoId, EstadoPago estadoPago) {
        String sql = "UPDATE carritos SET estadoPago = :estadoPago WHERE id = :carritoId;";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            con.createQuery(sql)
                    .addParameter("estadoPago", estadoPago)
                    .addParameter("carritoId", carritoId)
                    .executeUpdate();
        }
    }

    public void actualizarPreferenceMp(int carritoId, String preferenceIdMp) {  
        String sql = "UPDATE carritos SET preferenceIdMp = :preferenceIdMp WHERE id = :carritoId;";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            con.createQuery(sql)
                    .addParameter("preferenceIdMp", preferenceIdMp)
                    .addParameter("carritoId", carritoId)
                    .executeUpdate();
        }
    }

    public void actualizarFechaCompra(int carritoId) {
        String sql = "UPDATE carritos SET fechaCompra = NOW() WHERE id = :carritoId;";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            con.createQuery(sql)
                    .addParameter("carritoId", carritoId)
                    .executeUpdate();
        }
    }
}