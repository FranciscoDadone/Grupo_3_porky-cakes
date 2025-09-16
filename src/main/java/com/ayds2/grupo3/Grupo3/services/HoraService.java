package com.ayds2.grupo3.Grupo3.services;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class HoraService {
    public String calcularHora(String fecha, String origen, String destino) {
        LocalDateTime fechaLocal = LocalDateTime.parse(fecha);
        
        ZoneId zonaOrigen = ZoneId.of(origen);
        ZoneId zonaDestino = ZoneId.of(destino);
        
        ZonedDateTime fechaOrigen = fechaLocal.atZone(zonaOrigen);
        
        ZonedDateTime fechaDestino = fechaOrigen.withZoneSameInstant(zonaDestino);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        
        return fechaDestino.format(formatter);
                    
    }
}
