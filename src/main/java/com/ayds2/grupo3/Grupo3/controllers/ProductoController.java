package com.ayds2.grupo3.Grupo3.controllers;

import org.springframework.web.bind.annotation.RestController;
import com.ayds2.grupo3.Grupo3.models.Producto;
import com.ayds2.grupo3.Grupo3.services.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@AllArgsConstructor
@RestController
@RequestMapping("/productos")
public class ProductoController {
    private final ProductoService productoService;

    @GetMapping("/{id}")
    public Producto getProductoPorId(@PathVariable int id) {
        return productoService.getProductoPorId(id);
    }
}
