package com.ayds2.grupo3.Grupo3.services;

import org.springframework.stereotype.Service;

import com.ayds2.grupo3.Grupo3.dao.CarritoDAO;
import com.ayds2.grupo3.Grupo3.models.Carrito;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class CarritoService {
    private final CarritoDAO carritoDAO;

    public void agregarProducto(int id, int cantidad, int clienteId) {
        Carrito carrito = carritoDAO.getCarritoCliente(clienteId);
        if (carrito == null) {
            carrito = carritoDAO.crearCarrito(clienteId);
        }
        carritoDAO.agregarProducto(carrito.getId(), id, cantidad);
    }
}
