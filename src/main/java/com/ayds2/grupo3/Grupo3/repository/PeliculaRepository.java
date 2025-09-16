package com.ayds2.grupo3.Grupo3.repository;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;
import com.ayds2.grupo3.Grupo3.models.Pelicula;

@Repository
public class PeliculaRepository {
    private ArrayList<Pelicula> peliculas = new ArrayList<>();

    public ArrayList<Pelicula> getAll() {
        return this.getAll("");
    }

    public ArrayList<Pelicula> getAll(String buscar) {
        ArrayList<Pelicula> resultado = new ArrayList<>();
        for (Pelicula p : peliculas) {
            if (p.getTitulo().toLowerCase().contains(buscar.toLowerCase())) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public Pelicula getPelicula(int id) {
        for (Pelicula pelicula : peliculas) {
            if (pelicula.getId() == id) {
                return pelicula;
            }
        }
        return null;
    }

    public void create(Pelicula pelicula) {
        peliculas.add(pelicula);
    }

    public void delete(Pelicula pelicula) {
        peliculas.remove(pelicula);
    }
}
