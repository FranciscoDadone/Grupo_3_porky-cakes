package com.ayds2.grupo3.Grupo3.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayds2.grupo3.Grupo3.models.CodigoZip;
import com.ayds2.grupo3.Grupo3.services.CodigoZipService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
public class CodigoZipController {

    private CodigoZipService codigoZipService;

    @GetMapping("/codigoZip")
    public ResponseEntity<?> getCodigoZip(@RequestParam String codigoPais, @RequestParam String codigoPostal) {
        try {
            CodigoZip codigoZip = codigoZipService.codigoZip(codigoPais, codigoPostal);
            return ResponseEntity.ok(codigoZip);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno del servidor: " + e.getMessage()));
        }
    }
    
}