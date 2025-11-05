package com.ayds2.grupo3.Grupo3.services;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import com.ayds2.grupo3.Grupo3.controllers.CarritoController;
import com.ayds2.grupo3.Grupo3.dao.CarritoDAO;
import com.ayds2.grupo3.Grupo3.dao.ClienteDAO;
import com.ayds2.grupo3.Grupo3.dao.ProductoDAO;
import com.ayds2.grupo3.Grupo3.dto.ComprarCarritoDto;
import com.ayds2.grupo3.Grupo3.enums.EstadoPago;
import com.ayds2.grupo3.Grupo3.models.Carrito;
import com.ayds2.grupo3.Grupo3.models.Cliente;
import com.ayds2.grupo3.Grupo3.models.Producto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPApiException;


@Service
@RequiredArgsConstructor

public class CarritoService {
    private final CarritoDAO carritoDAO;
    private final ClienteDAO clienteDAO;
    private final ProductoDAO productoDAO;
    private final EnvioService envioService;
    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(CarritoService.class);

    @Value("${mercadopago.access.token}")
    private String accessToken;


    public void agregarProducto(int productoId, int cantidad, int clienteId) {
        Cliente cliente = clienteDAO.getPorId(clienteId);
        if (cliente == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente con ID " + clienteId + " no existe");
        }

        Producto producto = productoDAO.getPorId(productoId);
        if (producto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El producto con ID " + productoId + " no existe");
        }

        Carrito carrito = carritoDAO.getCarritoCliente(clienteId);
        if (carrito == null) {
            carrito = carritoDAO.crearCarrito(clienteId);
        }

        Integer cantidadActual = carritoDAO.cantidadDeProductoEnCarrito(carrito.getId(), productoId);

        if (cantidadActual == null) {
            carritoDAO.insertarProducto(carrito.getId(), productoId, cantidad);
        } else {
            carritoDAO.actualizarCantidadProducto(carrito.getId(), productoId, cantidadActual + cantidad);
        }
    }

    public String comprarCarrito(ComprarCarritoDto comprarCarritoDto) {
        Carrito carrito = carritoDAO.getCarritoPorId(comprarCarritoDto.getCarritoId());
        if (carrito == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El carrito con ID " + comprarCarritoDto.getCarritoId() + " no existe");
        }

        if (carrito.getFechaCompra() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El carrito con ID " + comprarCarritoDto.getCarritoId() + " ya fue comprado");
        }

        if (carrito.getPreferenceIdMp() != null) {
            return "https://www.mercadopago.com.ar/checkout/v1/redirect?pref_id=" + carrito.getPreferenceIdMp();
        }

        if (comprarCarritoDto.getCodigoPostal() == null || comprarCarritoDto.getProvincia() == null ||
                comprarCarritoDto.getLocalidad() == null || comprarCarritoDto.getDireccion() == null ||
                comprarCarritoDto.getNumero() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Faltan datos de envío obligatorios");
        }

        Producto[] productosCarrito = carritoDAO.getProductosDelCarrito(carrito.getId());

        String descripcion = "";
        for (Producto producto : productosCarrito) {
            descripcion += producto.getNombre() + ", ";
        }

        double total = carritoDAO.calcularTotalCarrito(carrito.getId());

        PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                .title(descripcion)
                .quantity(1)
                .currencyId("ARS")
                .unitPrice(new BigDecimal(total))
                .build();

        String externalReference = "PORKYS-" + System.currentTimeMillis() + "-" + String.valueOf(carrito.getId());

        logger.info("Generando preferencia de pago con referencia: {}", externalReference);

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .externalReference(externalReference)
                .items(List.of(itemRequest))
                .build();

        carritoDAO.actualizarExternalReferenceMp(carrito.getId(), externalReference);

        if (carrito.getEnvioId() == null) {
            Integer envioId = envioService.crearEnvio(
                    comprarCarritoDto.getCodigoPostal(),
                    comprarCarritoDto.getProvincia(),
                    comprarCarritoDto.getLocalidad(),
                    comprarCarritoDto.getDireccion(),
                    comprarCarritoDto.getNumero(),
                    comprarCarritoDto.getDescripcion()
            ).getId();

            carritoDAO.actualizarEnvioId(carrito.getId(), envioId);
        }

        PreferenceClient client = new PreferenceClient();
        
        try {
            Preference preference = client.create(preferenceRequest);
            carritoDAO.actualizarPreferenceMp(carrito.getId(), preference.getId());
            logger.info("Preferencia de pago creada con ID: {}", preference.getId());
            return "https://www.mercadopago.com.ar/checkout/v1/redirect?pref_id=" + preference.getId();
        } catch (MPException | MPApiException e) {
            logger.error("Error al crear la preferencia de pago: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear la preferencia de pago", e);
        }

    }

    public Carrito getCarritoPorId(int carritoId) {
        Carrito carrito = carritoDAO.getCarritoPorId(carritoId);
        if (carrito == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado");
        }
        return carrito;
    }

    public EstadoPago estadoPago(Carrito carrito) throws IOException, InterruptedException {
    
        HttpClient client = HttpClient.newHttpClient();

        logger.info("Obteniendo estado del pago con referencia: {}", carrito.getExternalReferenceMp());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.mercadopago.com/v1/payments/search?external_reference=" + carrito.getExternalReferenceMp()))
                .timeout(Duration.ofSeconds(10))
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        logger.info("Respuesta de MercadoPago: {}", response.body());

        if (response.statusCode() == 200) {
            Map<String, Object> jsonMap = objectMapper.readValue(response.body(),
                    new TypeReference<Map<String, Object>>() {
                    });

            List<?> results = (List<?>) jsonMap.get("results");
            
            if (results == null || results.isEmpty()) {
                return EstadoPago.PENDIENTE;
            }
            
            Object firstResult = results.get(0);
            if (!(firstResult instanceof Map)) {
                throw new RuntimeException("Formato de respuesta inesperado de MercadoPago");
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> payment = (Map<String, Object>) firstResult;
            String status = (String) payment.get("status");
            
            if ("approved".equals(status) && carrito.getFechaCompra() == null) {
                carritoDAO.marcarCarritoComoComprado(carrito.getId());
            }

            if ("approved".equals(status)) {
                return EstadoPago.COMPLETADO;
            }
            
            return EstadoPago.CANCELADO;

        } else {
            throw new RuntimeException("Error en la petición HTTP: " + response.statusCode());
        }

    }
}
