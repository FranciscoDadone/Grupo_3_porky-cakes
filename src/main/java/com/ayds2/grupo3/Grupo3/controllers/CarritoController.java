package com.ayds2.grupo3.Grupo3.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.ayds2.grupo3.Grupo3.services.CarritoService;
import com.ayds2.grupo3.Grupo3.dto.AgregarProductoRequest;
import com.ayds2.grupo3.Grupo3.dto.ComprarCarritoDto;
import lombok.AllArgsConstructor;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@RestController
@RequestMapping("/carrito")
public class CarritoController {
    private final CarritoService carritoService;

    @PostMapping("/productos")
    public ResponseEntity<Map<String, String>> agregarProducto(@RequestBody AgregarProductoRequest request) {
        try {
            carritoService.agregarProducto(request.getProductoId(), request.getCantidad(), request.getClienteId());
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getReason()));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("status", "Producto agregado al carrito"));
    }

    @PostMapping("/comprar")
    public ResponseEntity<Map<String, String>> comprarCarrito(@RequestBody ComprarCarritoDto request) {
        try {
            carritoService.comprarCarrito(request);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getReason()));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "Compra realizada con éxito"));
    }
}
