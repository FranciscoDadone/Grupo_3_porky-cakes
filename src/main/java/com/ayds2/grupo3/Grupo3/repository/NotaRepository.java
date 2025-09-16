package com.ayds2.grupo3.Grupo3.repository;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.ayds2.grupo3.Grupo3.models.Nota;

@Repository
public class NotaRepository {
    private ArrayList<Nota> notas = new ArrayList<>();

    public ArrayList<Nota> getAll() {
        return notas;
    }

    public Nota getNota(int id) {
        for (Nota nota : notas) {
            if (nota.getId() == id) {
                return nota;
            }
        }
        return null;
    }

    public void create(Nota nota) {
        notas.add(nota);
    }

    public void delete(Nota nota) {
        notas.remove(nota);
    }

    public void update(Nota notaActualizada) {
        for (int i = 0; i < notas.size(); i++) {
            if (notas.get(i).getId() == notaActualizada.getId()) {
                notas.set(i, notaActualizada);
                return;
            }
        }
        throw new IllegalArgumentException("La nota con ID " + notaActualizada.getId() + " no existe.");
    }
}
