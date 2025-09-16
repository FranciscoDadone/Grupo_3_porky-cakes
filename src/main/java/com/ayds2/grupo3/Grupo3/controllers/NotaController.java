package com.ayds2.grupo3.Grupo3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ayds2.grupo3.Grupo3.models.Nota;
import com.ayds2.grupo3.Grupo3.services.NotaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@AllArgsConstructor
@Controller
public class NotaController {

    private NotaService notaService;
    private ObjectMapper objectMapper;

    @GetMapping("/notas")
    @ResponseBody
    public String getNotas() {
        try {
            return objectMapper.writeValueAsString(notaService.obtenerNotas());
        } catch (JsonProcessingException e) {
            return "{\"error\": \"Error al convertir a JSON\"}";
        }
    }

    @PostMapping("/notas")
    @ResponseBody
    public String crearNota(@RequestBody String notaJson) {
        try {
            Nota nota = objectMapper.readValue(notaJson, Nota.class);
            notaService.crearNota(nota);
            return "{\"status\": \"Nota creada exitosamente\"}";
        } catch (IllegalArgumentException e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        } catch (JsonProcessingException e) {
            return "{\"error\": \"Error al procesar el JSON\"}";
        }   
    }

    @PutMapping("/notas/{id}")
    @ResponseBody
    public String actualizarNota(@PathVariable int id, @RequestBody String notaJson) {
        try {
            Nota nota = objectMapper.readValue(notaJson, Nota.class);
            notaService.actualizarNota(id, nota);
            return "{\"status\": \"Nota actualizada exitosamente\"}";
        } catch (NumberFormatException e) {
            return "{\"error\": \"ID inválido\"}";
        } catch (IllegalArgumentException e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        } catch (JsonProcessingException e) {
            return "{\"error\": \"Error al procesar el JSON\"}";
        }
    }

    @DeleteMapping("/notas/{id}")
    @ResponseBody
    public String eliminarNota(@PathVariable int id) {
        try {
            Nota nota = notaService.obtenerNotaPorId(id);
            if (nota != null) {
                notaService.eliminarNota(nota);
                return "{\"status\": \"Nota eliminada exitosamente\"}";
            } else {
                return "{\"error\": \"Nota no encontrada\"}";
            }
        } catch (Exception e) {
            return "{\"error\": \"Error al eliminar la nota\"}";
        }
    }
}
