package com.ayds2.grupo3.Grupo3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ayds2.grupo3.Grupo3.models.Usuario;
import com.ayds2.grupo3.Grupo3.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Controller
public class UsuarioController {

    private UsuarioService usuarioService;
    private ObjectMapper objectMapper;

    @GetMapping("/usuarioRandom")
    @ResponseBody
    public String getUsuarioRandom() {
        Usuario usuario = usuarioService.getUsuarioRandom();
        try {
            return objectMapper.writeValueAsString(usuario);
        } catch (Exception e) {
            return "{\"error\": \"Error al convertir a JSON\"}";
        }
    }
    
}
