package com.ayds2.grupo3.Grupo3.controllers;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ayds2.grupo3.Grupo3.services.HoraService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Controller
public class FechaController {

    private HoraService horaService;
    private ObjectMapper objectMapper;

    @GetMapping("/hora")
    @ResponseBody
    public String getHora(@RequestParam String fecha, @RequestParam String origen, @RequestParam String destino) {
        String horaCalculada = horaService.calcularHora(fecha, origen, destino);

        HashMap<String, String> response = new HashMap<>();
        response.put("origen", '"' + origen + '"');
        response.put("destino", '"' + horaCalculada + '"');

        try {
            return objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            return "{\"error\": \"Error al convertir a JSON\"}";
        }
    }
    
}
