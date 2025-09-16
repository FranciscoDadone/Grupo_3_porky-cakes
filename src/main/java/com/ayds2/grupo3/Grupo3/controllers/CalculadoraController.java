package com.ayds2.grupo3.Grupo3.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ayds2.grupo3.Grupo3.services.CalculadoraService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class CalculadoraController {

    private CalculadoraService calculadoraService;

    @GetMapping("/celsiusAfahrenheit/{celcius}")
    public ResponseEntity<Map<String, Object>> getCelsiusFarenheit(@PathVariable float celcius) {
        try {
            float fahrenheit = calculadoraService.celsiusToFahrenheit(celcius);

            Map<String, Object> response = new HashMap<>();
            response.put("celsius", celcius);
            response.put("fahrenheit", fahrenheit);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al convertir temperatura: " + e.getMessage()));
        }
    }

    @GetMapping("/esPrimo/{numero}")
    public ResponseEntity<Map<String, Object>> esPrimo(@PathVariable int numero) {
        try {
            boolean esPrimo = calculadoraService.esPrimo(numero);
            
            Map<String, Object> response = new HashMap<>();
            response.put("numero", numero);
            response.put("esPrimo", esPrimo);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al verificar si es primo: " + e.getMessage()));
        }
    }
}
