package com.ayds2.grupo3.Grupo3.repository;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.ayds2.grupo3.Grupo3.models.Libro;

@Repository
public class LibroRepository {
    private ArrayList<Libro> libros = new ArrayList<>();

    public ArrayList<Libro> getAll() {
        return libros;
    }

    public Libro getLibro(int id) {
        for (Libro libro : libros) {
            if (libro.getId() == id) {
                return libro;
            }
        }
        return null;
    }

    public void create(Libro libro) {
        libros.add(libro);
    }

    public void delete(Libro libro) {
        libros.remove(libro);
    }
}
