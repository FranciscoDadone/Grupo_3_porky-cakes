package com.ayds2.grupo3.Grupo3.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.ayds2.grupo3.Grupo3.models.Nota;
import com.ayds2.grupo3.Grupo3.repository.NotaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class NotaService {

    private NotaRepository notaRepository;

    public ArrayList<Nota> obtenerNotas() {
        return notaRepository.getAll();
    }

    public void crearNota(Nota nota) {
        notaRepository.create(nota);
    }

    public void eliminarNotaPorId(int id) {
        Nota nota = notaRepository.getNota(id);
        if (nota != null) {
            notaRepository.delete(nota);
        }
    }

    public Nota obtenerNotaPorId(int id) {
        return notaRepository.getNota(id);
    }

    public void actualizarNota(int id, Nota notaActualizada) {
        Nota notaExistente = notaRepository.getNota(id);
        if (notaExistente == null) {
            throw new IllegalArgumentException("La nota con ID " + id + " no existe.");
        }
        notaActualizada.setId(id);
        notaRepository.update(notaActualizada);
    }
}
