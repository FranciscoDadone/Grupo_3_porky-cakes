package com.ayds2.grupo3.Grupo3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ayds2.grupo3.Grupo3.models.CodigoZip;
import com.ayds2.grupo3.Grupo3.services.CodigoZipService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Controller
public class CodigoZipController {

    private CodigoZipService codigoZipService;
    private ObjectMapper objectMapper;

    @GetMapping("/codigoZip")
    @ResponseBody
    public String getCodigoZip(@RequestParam String codigoPais, @RequestParam String codigoPostal) {
        CodigoZip codigoZip = codigoZipService.codigoZip(codigoPais, codigoPostal);
        try {
            return objectMapper.writeValueAsString(codigoZip);
        } catch (Exception e) {
            return "{\"error\": \"Error al convertir a JSON\"}";
        }
    }
    
}