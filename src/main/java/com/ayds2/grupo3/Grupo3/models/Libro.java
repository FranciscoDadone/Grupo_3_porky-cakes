package com.ayds2.grupo3.Grupo3.models;

import lombok.Data;

@Data
public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private int anioPublicacion;
    
    public Libro(int id, String titulo, String autor, int anioPublicacion) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
    }
}
