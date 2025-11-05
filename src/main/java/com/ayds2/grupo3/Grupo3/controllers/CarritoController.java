package com.ayds2.grupo3.Grupo3.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.ayds2.grupo3.Grupo3.services.CarritoService;
import com.ayds2.grupo3.Grupo3.services.EnvioService;
import com.ayds2.grupo3.Grupo3.dto.AgregarProductoRequest;
import com.ayds2.grupo3.Grupo3.dto.ComprarCarritoDto;
import com.ayds2.grupo3.Grupo3.enums.EstadoPago;
import com.ayds2.grupo3.Grupo3.models.Carrito;
import com.ayds2.grupo3.Grupo3.models.Envio;
import lombok.AllArgsConstructor;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@AllArgsConstructor
@RestController
@RequestMapping("/carrito")
public class CarritoController {
    private final CarritoService carritoService;
    private final EnvioService envioService;
    private static final Logger logger = LoggerFactory.getLogger(CarritoController.class);

    @PostMapping("/productos")
    public ResponseEntity<Map<String, String>> agregarProducto(@RequestBody AgregarProductoRequest request) {
        logger.info("Agregando productos al carrito: {}", request);

        try {
            carritoService.agregarProducto(request.getProductoId(), request.getCantidad(), request.getClienteId());
        } catch (ResponseStatusException e) {
            logger.error("Error al agregar producto al carrito: {}", e.getReason());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getReason()));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("status", "Producto agregado al carrito"));
    }

    @PostMapping("/comprar")
    public ResponseEntity<Map<String, String>> comprarCarrito(@RequestBody ComprarCarritoDto request) {
        logger.info("Comprando carrito: {}", request);

        try {
            String linkMercadoPago = carritoService.comprarCarrito(request);

            return ResponseEntity.status(HttpStatus.OK).body(Map.of("link", linkMercadoPago));
        } catch (ResponseStatusException e) {
            logger.error("Error al comprar carrito: {}", e.getReason());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getReason()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getEstado(@PathVariable int id) {
        try {
            Carrito carrito = carritoService.getCarritoPorId(id);
            EstadoPago estado = carritoService.estadoPago(carrito);
            Envio envio = carrito.getEnvioId() != null ? envioService.getEnvioPorId(carrito.getEnvioId()) : null;
            
            if (envio != null) {
                return ResponseEntity.ok(Map.of("carrito", carrito, "estado", estado, "envio", envio));
            } else {
                return ResponseEntity.ok(Map.of("carrito", carrito, "estado", estado));
            }
            
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getReason()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }
    
}
