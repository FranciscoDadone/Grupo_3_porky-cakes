package com.ayds2.grupo3.Grupo3.models;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
