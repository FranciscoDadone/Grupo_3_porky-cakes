package com.ayds2.grupo3.Grupo3.models;

public class Nota {
    private int id;
    private String titulo;
    private String contenido;
    private int fechaCreacion;

    public Nota(int id, String titulo, String contenido, int fechaCreacion) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fechaCreacion = fechaCreacion;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public int getFechaCreacion() {
        return fechaCreacion;
    }
}
