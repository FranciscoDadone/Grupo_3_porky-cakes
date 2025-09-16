package com.ayds2.grupo3.Grupo3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ayds2.grupo3.Grupo3.models.Libro;
import com.ayds2.grupo3.Grupo3.services.LibroService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@AllArgsConstructor
@Controller
public class LibroController {

    private LibroService libroService;
    private ObjectMapper objectMapper;

    @GetMapping("/libros")
    @ResponseBody
    public String getLibros() {
        try {
            return objectMapper.writeValueAsString(libroService.obtenerLibros());
        } catch (JsonProcessingException e) {
            return "{\"error\": \"Error al convertir a JSON\"}";
        }
    }

    @GetMapping("/libros/{id}")
    @ResponseBody
    public String getLibroById(@PathVariable int id) {
        try {
            return objectMapper.writeValueAsString(libroService.obtenerLibroPorId(id));
        } catch (JsonProcessingException e) {
            return "{\"error\": \"Error al convertir a JSON\"}";
        }
    }

    @PostMapping("/libros")
    @ResponseBody
    public String crearLibro(@RequestBody String libroJson) {
        try {
            Libro libro = objectMapper.readValue(libroJson, Libro.class);
            libroService.crearLibro(libro);
            return "{\"status\": \"Libro creado exitosamente\"}";
        } catch (IllegalArgumentException e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        } catch (JsonProcessingException e) {
            return "{\"error\": \"Error al procesar el JSON\"}";
        }   
    }

    @DeleteMapping("/libros/{id}")
    @ResponseBody
    public String eliminarLibro(@PathVariable int id) {
        try {
            Libro libro = libroService.obtenerLibroPorId(id);
            if (libro != null) {
                libroService.eliminarLibro(libro);
                return "{\"status\": \"Libro eliminado exitosamente\"}";
            } else {
                return "{\"error\": \"Libro no encontrado\"}";
            }
        } catch (Exception e) {
            return "{\"error\": \"Error al eliminar el libro\"}";
        }
    }
}
