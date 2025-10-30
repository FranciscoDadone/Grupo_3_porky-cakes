package com.ayds2.grupo3.Grupo3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.mercadopago.MercadoPagoConfig;
import jakarta.annotation.PostConstruct;


@SpringBootApplication
public class Grupo3Application {

	@Value("${mercadopago.access.token}")
    private String accessToken;

	@PostConstruct
	public void init() {
		MercadoPagoConfig.setAccessToken(accessToken);
	}

	public static void main(String[] args) {
		SpringApplication.run(Grupo3Application.class, args);
	}

}
