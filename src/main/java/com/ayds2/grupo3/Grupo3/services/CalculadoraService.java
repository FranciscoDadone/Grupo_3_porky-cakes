package com.ayds2.grupo3.Grupo3.services;

import org.springframework.stereotype.Service;

@Service
public class CalculadoraService {

    public float celsiusToFahrenheit(float celsius) {
        return (celsius * 9 / 5) + 32;
    }

    public boolean esPrimo(int numero) {
        if (numero <= 1) {
            return false;
        }
        for (int i = 2; i < numero; i++) {
            if (numero % i == 0) {
                return false;
            }
        }
        return true;
    }
}
