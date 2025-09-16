package com.ayds2.grupo3.Grupo3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ayds2.grupo3.Grupo3.models.Pelicula;
import com.ayds2.grupo3.Grupo3.services.PeliculaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@AllArgsConstructor
@Controller
public class PeliculaController {

    private PeliculaService peliculaService;
    private ObjectMapper objectMapper;

    @GetMapping("/peliculas")
    @ResponseBody
    public String getPeliculas() {
        try {
            return objectMapper.writeValueAsString(peliculaService.obtenerPeliculas());
        } catch (JsonProcessingException e) {
            return "{\"error\": \"Error al convertir a JSON\"}";
        }
    }

    @GetMapping("/peliculas/buscar")
    @ResponseBody
    public String getPeliculaById(@RequestParam String titulo) {
        try {
            return objectMapper.writeValueAsString(peliculaService.obtenerPeliculas(titulo));
        } catch (JsonProcessingException e) {
            return "{\"error\": \"Error al convertir a JSON\"}";
        }
    }

    @PostMapping("/peliculas")
    @ResponseBody
    public String crearPelicula(@RequestBody String peliculaJson) {
        try {
            Pelicula pelicula = objectMapper.readValue(peliculaJson, Pelicula.class);
            peliculaService.crearPelicula(pelicula);
            return "{\"status\": \"Pelicula creado exitosamente\"}";
        } catch (IllegalArgumentException e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        } catch (JsonProcessingException e) {
            return "{\"error\": \"Error al procesar el JSON\"}";
        }   
    }

    @DeleteMapping("/peliculas/{id}")
    @ResponseBody
    public String eliminarPelicula(@PathVariable int id) {
        try {
            Pelicula pelicula = peliculaService.obtenerPeliculaPorId(id);
            if (pelicula != null) {
                peliculaService.eliminarPelicula(pelicula);
                return "{\"status\": \"Pelicula eliminado exitosamente\"}";
            } else {
                return "{\"error\": \"Pelicula no encontrado\"}";
            }
        } catch (Exception e) {
            return "{\"error\": \"Error al eliminar el pelicula\"}";
        }
    }
}
