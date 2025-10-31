package com.ayds2.grupo3.Grupo3.services;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import com.ayds2.grupo3.Grupo3.dao.CarritoDAO;
import com.ayds2.grupo3.Grupo3.dao.ClienteDAO;
import com.ayds2.grupo3.Grupo3.dao.ProductoDAO;
import com.ayds2.grupo3.Grupo3.dto.ComprarCarritoDto;
import com.ayds2.grupo3.Grupo3.models.Carrito;
import com.ayds2.grupo3.Grupo3.models.Cliente;
import com.ayds2.grupo3.Grupo3.models.Producto;
import lombok.AllArgsConstructor;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPApiException;


@Service
@AllArgsConstructor

public class CarritoService {
    private final CarritoDAO carritoDAO;
    private final ClienteDAO clienteDAO;
    private final ProductoDAO productoDAO;
    private final EnvioService envioService;

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
            return "https://www.mercadopago.com.ar/checkout/v1/redirect?pref_id=" + preference.getId();
        } catch (MPException | MPApiException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear la preferencia de pago", e);
        }

    }
}
