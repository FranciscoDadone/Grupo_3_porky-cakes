package com.ayds2.grupo3.Grupo3.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ayds2.grupo3.Grupo3.services.HoraService;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
public class FechaController {

    private HoraService horaService;

    @GetMapping("/hora")
    public ResponseEntity<?> getHora(@RequestParam String fecha, @RequestParam String origen, @RequestParam String destino) {
        try {
            String horaCalculada = horaService.calcularHora(fecha, origen, destino);

            Map<String, String> response = new HashMap<>();
            response.put("origen", fecha);
            response.put("destino", horaCalculada);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno del servidor"));
        }
    }
    
}
