package com.ayds2.grupo3.Grupo3.controllers;

import org.springframework.web.bind.annotation.RestController;
import com.ayds2.grupo3.Grupo3.services.CarritoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@RestController
@RequestMapping("/carrito")
public class CarritoController {
    private final CarritoService carritoService;

    @PostMapping("/productos")
    public void agregarProducto(@RequestBody int productoId, @RequestBody int cantidad, @RequestBody int clienteId) {
        carritoService.agregarProducto(productoId, cantidad, clienteId);
    }
}
