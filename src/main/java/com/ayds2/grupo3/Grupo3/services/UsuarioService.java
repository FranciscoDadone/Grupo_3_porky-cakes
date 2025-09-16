package com.ayds2.grupo3.Grupo3.services;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ayds2.grupo3.Grupo3.models.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UsuarioService {

    private ObjectMapper objectMapper;

    public Usuario getUsuarioRandom() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://dummyjson.com/users/1"))
                .timeout(Duration.ofSeconds(10))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Map<String, Object> jsonMap = objectMapper.readValue(response.body(),
                    new TypeReference<Map<String, Object>>() {
                    });

            Usuario usuario = new Usuario((int) jsonMap.get("id"), (String) jsonMap.get("firstName"),
                    (String) jsonMap.get("email"), (String) jsonMap.get("image"));

            return usuario;

        } else {
            throw new RuntimeException("Error en la petición HTTP: " + response.statusCode());
        }
    }
}
