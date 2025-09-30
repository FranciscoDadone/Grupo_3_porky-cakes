package com.ayds2.grupo3.Grupo3.models;

import lombok.Data;

@Data
public class Producto {
    private int id;
    private String nombre;
    private String descripcion;
    private String urlImagen;
    private double precio;
    private int stock;
    private boolean sinTacc;
    private boolean aptoVegano;
}
