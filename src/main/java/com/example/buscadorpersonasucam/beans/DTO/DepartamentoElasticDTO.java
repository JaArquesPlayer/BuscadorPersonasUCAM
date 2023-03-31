package com.example.buscadorpersonasucam.beans.DTO;

import java.io.Serializable;

public class DepartamentoElasticDTO implements Serializable {
    public DepartamentoElasticDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private String nombre;
}
