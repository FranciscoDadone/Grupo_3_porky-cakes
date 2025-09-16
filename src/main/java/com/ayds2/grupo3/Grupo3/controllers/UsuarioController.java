package com.ayds2.grupo3.Grupo3.controllers;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ayds2.grupo3.Grupo3.models.Usuario;
import com.ayds2.grupo3.Grupo3.services.UsuarioService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class UsuarioController {

    private UsuarioService usuarioService;

    @GetMapping("/usuarioRandom")
    public ResponseEntity<?> getUsuarioRandom() {
        Usuario usuario;
        try {
            usuario = usuarioService.getUsuarioRandom();
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al obtener el usuario: " + e.getMessage()));
        }
        return ResponseEntity.ok(usuario);
    }
    
}
