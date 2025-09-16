package com.ayds2.grupo3.Grupo3.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.ayds2.grupo3.Grupo3.models.CodigoZip;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CodigoZipService {
    private ObjectMapper objectMapper;

    public CodigoZip codigoZip(String codigoPais, String codigoPostal) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://api.zippopotam.us/" + codigoPais + "/" + codigoPostal))
                    .timeout(Duration.ofSeconds(10))
                    .header("Accept", "application/json")
                    .GET()
                    .build();
            
            HttpResponse<String> response = client.send(request, 
                    HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                Map<String, Object> jsonMap = objectMapper.readValue(response.body(), new TypeReference<Map<String, Object>>() {});

                CodigoZip codigoZip = new CodigoZip((String)jsonMap.get("post code"), (String)jsonMap.get("country"));
                

                return codigoZip;

            } else {
                throw new RuntimeException("Error en la petición HTTP: " + response.statusCode());
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener usuario: " + e.getMessage(), e);
        }
    }
}
