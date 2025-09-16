package com.ayds2.grupo3.Grupo3.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.ayds2.grupo3.Grupo3.models.Pelicula;
import com.ayds2.grupo3.Grupo3.services.PeliculaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@AllArgsConstructor
@RestController
public class PeliculaController {

    private PeliculaService peliculaService;
    private ObjectMapper objectMapper;

    @GetMapping("/peliculas")
    public ResponseEntity<?> getPeliculas() {
        try {
            return ResponseEntity.ok(peliculaService.obtenerPeliculas());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al obtener las películas"));
        }
    }

    @GetMapping("/peliculas/buscar")
    public ResponseEntity<?> getPeliculaById(@RequestParam String titulo) {
        try {
            return ResponseEntity.ok(peliculaService.obtenerPeliculas(titulo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al buscar la película"));
        }
    }

    @PostMapping("/peliculas")
    public ResponseEntity<?> crearPelicula(@RequestBody String peliculaJson) {
        try {
            Pelicula pelicula = objectMapper.readValue(peliculaJson, Pelicula.class);
            peliculaService.crearPelicula(pelicula);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("status", "Película creada exitosamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Error al procesar el JSON"));
        }   
    }

    @DeleteMapping("/peliculas/{id}")
    public ResponseEntity<?> eliminarPelicula(@PathVariable int id) {
        try {
            Pelicula pelicula = peliculaService.obtenerPeliculaPorId(id);
            if (pelicula != null) {
                peliculaService.eliminarPelicula(pelicula);
                return ResponseEntity.ok(Map.of("status", "Película eliminada exitosamente"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Película no encontrada"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al eliminar la película"));
        }
    }
}
