package com.ayds2.grupo3.Grupo3.dto;

import lombok.Data;

@Data
public class AgregarProductoRequest {
    private int productoId;
    private int cantidad;
    private int clienteId;
}
