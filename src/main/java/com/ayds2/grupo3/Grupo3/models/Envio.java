package com.ayds2.grupo3.Grupo3.models;

import lombok.Data;

@Data
public class Envio {
    private Integer id;
    private Integer codigoPostal;
    private String provincia;
    private String localidad;
    private String direccion;
    private Integer numero;
    private String descripcion;
    private String seguimiento;
    private String estado;
}
