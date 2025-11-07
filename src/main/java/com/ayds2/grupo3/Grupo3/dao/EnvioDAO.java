package com.ayds2.grupo3.Grupo3.dao;
import org.springframework.stereotype.Repository;
import com.ayds2.grupo3.Grupo3.models.Envio;

@Repository
public class EnvioDAO extends CrudDAO<Envio> {
    @Override
    public Class<Envio> getTClass() {
        return Envio.class;
    }

    @Override
    public String getTablePK() {
        return "id";
    }

    @Override
    public String getTableName() {
        return "envios";
    }
}
