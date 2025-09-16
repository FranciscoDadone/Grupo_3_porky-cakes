package com.ayds2.grupo3.Grupo3.models;

import lombok.Data;

@Data
public class CodigoZip {
    private String codigo;
    private String pais;

    public CodigoZip(String codigo, String pais) {
        this.codigo = codigo;
        this.pais = pais;
    }
}
