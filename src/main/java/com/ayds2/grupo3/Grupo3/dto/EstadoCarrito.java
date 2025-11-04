package com.ayds2.grupo3.Grupo3.dto;

import com.ayds2.grupo3.Grupo3.enums.EstadoPago;
import com.ayds2.grupo3.Grupo3.models.Carrito;

import lombok.Data;

@Data
public class EstadoCarrito {
    private Carrito carrito;
    private EstadoPago estadoPago;
}
