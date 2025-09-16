package com.ayds2.grupo3.Grupo3.models;

import lombok.Data;

@Data
public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String imagen;

    public Usuario(int id, String nombre, String email, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.imagen = imagen;
    }
}
