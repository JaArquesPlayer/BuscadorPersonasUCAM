package com.example.buscadorpersonasucam.beans.DTO;

import java.io.Serializable;

public class AsignaturaElasticDTO implements Serializable {
    public AsignaturaElasticDTO() {
    }

    public String getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(String idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private String idAsignatura;
    private String nombre;
}
