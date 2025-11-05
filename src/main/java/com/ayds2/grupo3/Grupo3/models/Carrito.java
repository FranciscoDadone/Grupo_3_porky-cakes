package com.ayds2.grupo3.Grupo3.models;

import java.sql.Timestamp;

import com.ayds2.grupo3.Grupo3.enums.EstadoPago;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Carrito {
    private int id;
    private int clienteId;
    private Timestamp fechaCompra;
    private Integer envioId;
    private String externalReferenceMp;
    private String preferenceIdMp;
    private EstadoPago estadoPago;
}
