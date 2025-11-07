package com.ayds2.grupo3.Grupo3.services;

import org.springframework.stereotype.Service;
import com.ayds2.grupo3.Grupo3.dao.EnvioDAO;
import com.ayds2.grupo3.Grupo3.models.Envio;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EnvioService {
    private final EnvioDAO envioDAO;

    public Envio crearEnvio(Integer codigoPostal, String provincia, String localidad, String direccion, Integer numero,
            String descripcion) {

        Envio envio = new Envio();
        envio.setCodigoPostal(codigoPostal);
        envio.setProvincia(provincia);
        envio.setLocalidad(localidad);
        envio.setDireccion(direccion);
        envio.setNumero(numero);
        envio.setDescripcion(descripcion);
        envio.setEstado("CREADO");

        int id = envioDAO.insert(envio).intValue();
        
        envio.setId(id);

        return envio;
    }

    public Envio getEnvioPorId(int envioId) {
        return envioDAO.select(envioId);
    }
}
