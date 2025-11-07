package com.ayds2.grupo3.Grupo3.dto;

import lombok.Data;

@Data
public class ProductoCarritoDto {
    private int id;
    private String nombre;
    private double precio;
    private int cantidad;
    private int stock;
}
