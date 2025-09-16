package com.ayds2.grupo3.Grupo3.models;

import lombok.Data;

@Data
public class Pelicula {
    private int id;
    private String titulo;
    private String director;
    private int anio;
    private String genero;

    public Pelicula(int id, String titulo, String director, int anio, String genero) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.anio = anio;
        this.genero = genero;
    }
}
