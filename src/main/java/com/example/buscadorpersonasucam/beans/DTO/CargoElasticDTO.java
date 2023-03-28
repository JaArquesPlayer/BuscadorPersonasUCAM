package com.example.buscadorpersonasucam.beans.DTO;

import java.io.Serializable;

public class CargoElasticDTO implements Serializable {
    public CargoElasticDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoCargo() {
        return tipoCargo;
    }

    public void setTipoCargo(String tipoCargo) {
        this.tipoCargo = tipoCargo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    private String nombre;
    private String tipoCargo;
    private String departamento;
}
