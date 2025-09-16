package com.ayds2.grupo3.Grupo3.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ayds2.grupo3.Grupo3.services.CalculadoraService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class CalculadoraController {

    private CalculadoraService calculadoraService;

    @GetMapping("/celsiusAfahrenheit/{celcius}")
    public Map<String, Float> getCelsiusFarenheit(@PathVariable float celcius) {
        float fahrenheit = calculadoraService.celsiusToFahrenheit(celcius);

        Map<String, Float> response = new HashMap<>();
        response.put("celsius", celcius);
        response.put("fahrenheit", fahrenheit);

        return response;
    }

    @GetMapping("/esPrimo/{numero}")
    public Map<String, Object> esPrimo(@PathVariable int numero) {
        boolean esPrimo = calculadoraService.esPrimo(numero);
        
        Map<String, Object> response = new HashMap<>();
        response.put("numero", numero);
        response.put("esPrimo", esPrimo);
        return response;
    }
}
