package com.example.buscadorpersonasucam.beans.DTO;

import java.io.Serializable;

public class AsignaturaDTO implements Serializable {
    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    private String nombreAsignatura;
    private String fechaFin;
}
