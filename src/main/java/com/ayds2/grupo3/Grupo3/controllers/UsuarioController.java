package com.ayds2.grupo3.Grupo3.controllers;

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
        try {
            Usuario usuario = usuarioService.getUsuarioRandom();
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno del servidor"));
        }
    }
    
}
