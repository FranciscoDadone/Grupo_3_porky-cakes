package com.ayds2.grupo3.Grupo3.dto;

import lombok.Data;

@Data
public class ComprarCarritoDto {
    private int carritoId;
    private int codigoPostal;
    private int provinciaId;
    private int localidadId;
    private String direccion;
    private int numero;
    private String descripcion;
}
