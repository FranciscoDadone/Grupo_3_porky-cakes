package com.ayds2.grupo3.Grupo3.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.ayds2.grupo3.Grupo3.models.Libro;
import com.ayds2.grupo3.Grupo3.repository.LibroRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class LibroService {

    private LibroRepository libroRepository;

    public ArrayList<Libro> obtenerLibros() {
        return libroRepository.getAll();
    }

    public Libro obtenerLibroPorId(int id) {
        return libroRepository.getLibro(id);
    }

    public void crearLibro(Libro libro) {
        Libro l = libroRepository.getLibro(libro.getId());
        if (l != null) {
            throw new IllegalArgumentException("El libro con ID " + libro.getId() + " ya existe.");
        }
        libroRepository.create(libro);
    }

    public void eliminarLibro(Libro libro) {
        libroRepository.delete(libro);
    }
}
