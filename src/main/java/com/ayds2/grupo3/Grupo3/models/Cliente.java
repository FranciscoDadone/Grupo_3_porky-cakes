package com.ayds2.grupo3.Grupo3.models;

import lombok.Data;

@Data
public class Cliente {
    private int id;
    private String nombre;
    private String email;
    private String direccion;
    private String provincia;
    private String localidad;
}
