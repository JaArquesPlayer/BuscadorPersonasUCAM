package com.example.buscadorpersonasucam.beans.DTO;

import java.io.Serializable;

public class AutorDTO implements Serializable {
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private Integer orden;
    private String nombreCompleto;
}
