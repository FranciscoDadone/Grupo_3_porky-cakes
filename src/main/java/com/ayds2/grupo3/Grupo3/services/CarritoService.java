package com.ayds2.grupo3.Grupo3.services;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.ayds2.grupo3.Grupo3.dao.CarritoDAO;
import com.ayds2.grupo3.Grupo3.dao.ClienteDAO;
import com.ayds2.grupo3.Grupo3.dao.ProductoDAO;
import com.ayds2.grupo3.Grupo3.models.Carrito;
import com.ayds2.grupo3.Grupo3.models.Cliente;
import com.ayds2.grupo3.Grupo3.models.Producto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class CarritoService {
    private final CarritoDAO carritoDAO;
    private final ClienteDAO clienteDAO;
    private final ProductoDAO productoDAO;

    public void agregarProducto(int productoId, int cantidad, int clienteId) {
        Cliente cliente = clienteDAO.getPorId(clienteId);
        if (cliente == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente con ID " + clienteId + " no existe");
        }

        Producto producto = productoDAO.getPorId(productoId);
        if (producto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto con ID " + productoId + " no existe");
        }

        Carrito carrito = carritoDAO.getCarritoCliente(clienteId);
        if (carrito == null) {
            carrito = carritoDAO.crearCarrito(clienteId);
        }

        Integer cantidadActual = carritoDAO.cantidadDeProductoEnCarrito(carrito.getId(), productoId);

        if (cantidadActual == null) {
            carritoDAO.insertarProducto(carrito.getId(), productoId, cantidad);
        } else {
            carritoDAO.actualizarCantidadProducto(carrito.getId(), productoId, cantidadActual + cantidad);
        }

    }
}
