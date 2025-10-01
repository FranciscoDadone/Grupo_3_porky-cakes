package com.ayds2.grupo3.Grupo3.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Carrito {
    private int id;
    private int clienteId;
    private boolean comprado;
}
