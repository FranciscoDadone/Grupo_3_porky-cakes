package com.ayds2.grupo3.Grupo3.dao;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import com.ayds2.grupo3.Grupo3.models.Envio;
import com.ayds2.grupo3.Grupo3.util.Sql2oDAO;

@Repository
public class EnvioDAO {
    public Envio guardarEnvio(Envio envio) {
        String sql = "INSERT INTO envios (codigoPostal, provincia, localidad, direccion, numero, descripcion, seguimiento, estado) VALUES (:codigoPostal, :provincia, :localidad, :direccion, :numero, :descripcion, :seguimiento, :estado);";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            Object key = con.createQuery(sql)
                    .addParameter("codigoPostal", envio.getCodigoPostal())
                    .addParameter("provincia", envio.getProvincia())
                    .addParameter("localidad", envio.getLocalidad())
                    .addParameter("direccion", envio.getDireccion())
                    .addParameter("numero", envio.getNumero())
                    .addParameter("descripcion", envio.getDescripcion())
                    .addParameter("seguimiento", envio.getSeguimiento())
                    .addParameter("estado", envio.getEstado())
                    .executeUpdate()
                    .getKey();
            
            Integer generatedId = ((Number) key).intValue();
            envio.setId(generatedId);
        }
        return envio;
    }

    public Envio getEnvioPorId(int envioId) {
        String sql = "SELECT * FROM envios WHERE id = :envioId;";
        try (Connection con = Sql2oDAO.getSql2o().open()) {
            return con.createQuery(sql)
                    .addParameter("envioId", envioId)
                    .executeAndFetchFirst(Envio.class);
        }
    }
}
