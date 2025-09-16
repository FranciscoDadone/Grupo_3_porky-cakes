package com.ayds2.grupo3.Grupo3.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.ayds2.grupo3.Grupo3.models.Libro;
import com.ayds2.grupo3.Grupo3.services.LibroService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RestController
public class LibroController {

    private LibroService libroService;
    private ObjectMapper objectMapper;

    @GetMapping("/libros")
    public ResponseEntity<?> getLibros() {
        try {
            return ResponseEntity.ok(libroService.obtenerLibros());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al obtener los libros"));
        }
    }

    @GetMapping("/libros/{id}")
    public ResponseEntity<?> getLibroById(@PathVariable int id) {
        try {
            Libro libro = libroService.obtenerLibroPorId(id);
            if (libro != null) {
                return ResponseEntity.ok(libro);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Libro no encontrado"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al obtener el libro"));
        }
    }

    @PostMapping("/libros")
    public ResponseEntity<?> crearLibro(@RequestBody String libroJson) {
        try {
            Libro libro = objectMapper.readValue(libroJson, Libro.class);
            libroService.crearLibro(libro);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("status", "Libro creado exitosamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Error al procesar el JSON"));
        }   
    }

    @DeleteMapping("/libros/{id}")
    public ResponseEntity<?> eliminarLibro(@PathVariable int id) {
        try {
            Libro libro = libroService.obtenerLibroPorId(id);
            if (libro != null) {
                libroService.eliminarLibro(libro);
                return ResponseEntity.ok(Map.of("status", "Libro eliminado exitosamente"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Libro no encontrado"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al eliminar el libro"));
        }
    }
}
