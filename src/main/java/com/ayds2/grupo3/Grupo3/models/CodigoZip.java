package com.ayds2.grupo3.Grupo3.models;

public class CodigoZip {
    private String codigo;
    private String pais;

    public CodigoZip(String codigo, String pais) {
        this.codigo = codigo;
        this.pais = pais;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
