package com.ayds2.grupo3.Grupo3.dao;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import com.ayds2.grupo3.Grupo3.dto.ProductoCarritoDto;
import com.ayds2.grupo3.Grupo3.enums.EstadoPago;
import com.ayds2.grupo3.Grupo3.models.Carrito;
import com.ayds2.grupo3.Grupo3.util.Sql2oDAO;

@Repository
public class CarritoDAO extends CrudDAO<Carrito> {
    @Override
    public Class<Carrito> getTClass() {
        return Carrito.class;
    }

    @Override
    public String getTablePK() {
        return "id";
    }

    @Override
    public String getTableName() {
        return "carritos";
    }

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

    public ProductoCarritoDto[] getProductosDelCarrito(int carritoId) {
        String sql = "SELECT p.id, p.nombre, p.precio, p.stock, pc.cantidad " +
                     "FROM productos p " +
                     "JOIN productos_x_carrito pc ON p.id = pc.productoId " +
                     "WHERE pc.carritoId = :carritoId;";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            return con.createQuery(sql)
                    .addParameter("carritoId", carritoId)
                    .executeAndFetch(ProductoCarritoDto.class)
                    .toArray(new ProductoCarritoDto[0]);
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
        Carrito carrito = select(carritoId);
        if (carrito != null) {
            carrito.setExternalReferenceMp(externalReferenceMp);
            update(carrito);
        }
    }

    public void actualizarEnvioId(int carritoId, int envioId) {
        Carrito carrito = select(carritoId);
        if (carrito != null) {
            carrito.setEnvioId(envioId);
            update(carrito);
        }
    }

    public void actualizarEstadoPago(int carritoId, EstadoPago estadoPago) {
        Carrito carrito = select(carritoId);
        if (carrito != null) {
            carrito.setEstadoPago(estadoPago);
            update(carrito);
        }
    }

    public void actualizarPreferenceMp(int carritoId, String preferenceIdMp) {
        Carrito carrito = select(carritoId);
        if (carrito != null) {
            carrito.setPreferenceIdMp(preferenceIdMp);
            update(carrito);
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