package com.ayds2.grupo3.Grupo3.controllers;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.ayds2.grupo3.Grupo3.models.Nota;
import com.ayds2.grupo3.Grupo3.services.NotaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;

@AllArgsConstructor
@RestController
@RequestMapping("/notas")
public class NotaController {

    private NotaService notaService;
    private ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<?> getNotas() {
        try {
            return ResponseEntity.ok(notaService.obtenerNotas());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al obtener las notas"));
        }
    }

    @PostMapping
    public ResponseEntity<?> crearNota(@RequestBody String notaJson) {
        try {
            Nota nota = objectMapper.readValue(notaJson, Nota.class);
            notaService.crearNota(nota);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("status", "Nota creada exitosamente"));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Error al procesar el JSON"));
        }   
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarNota(@PathVariable int id, @RequestBody String notaJson) {
        try {
            Nota nota = objectMapper.readValue(notaJson, Nota.class);
            notaService.actualizarNota(id, nota);
            return ResponseEntity.ok(Map.of("status", "Nota actualizada exitosamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Error al procesar el JSON"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarNota(@PathVariable int id) {
        notaService.eliminarNotaPorId(id);
        return ResponseEntity.ok(Map.of("status", "Nota eliminada exitosamente"));
    }
}
