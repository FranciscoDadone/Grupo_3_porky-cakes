package com.ayds2.grupo3.Grupo3.services;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import com.ayds2.grupo3.Grupo3.models.Pelicula;
import com.ayds2.grupo3.Grupo3.repository.PeliculaRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PeliculaService {

    private PeliculaRepository peliculaRepository;

    public ArrayList<Pelicula> obtenerPeliculas() {
        return peliculaRepository.getAll();
    }

    public ArrayList<Pelicula> obtenerPeliculas(String buscar) {
        return peliculaRepository.getAll(buscar);
    }

    public Pelicula obtenerPeliculaPorId(int id) {
        return peliculaRepository.getPelicula(id);
    }

    public void crearPelicula(Pelicula pelicula) {
        Pelicula l = peliculaRepository.getPelicula(pelicula.getId());
        if (l != null) {
            throw new IllegalArgumentException("El pelicula con ID " + pelicula.getId() + " ya existe.");
        }
        peliculaRepository.create(pelicula);
    }

    public void eliminarPelicula(Pelicula pelicula) {
        peliculaRepository.delete(pelicula);
    }
}
