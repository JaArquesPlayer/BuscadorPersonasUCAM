package com.example.buscadorpersonasucam.beans.DTO;

import java.io.Serializable;
import java.util.List;

public class DocenciaDTO implements Serializable {
    public String getTitulacion() {
        return titulacion;
    }

    public void setTitulacion(String titulacion) {
        this.titulacion = titulacion;
    }

    public List<AsignaturaDTO> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(List<AsignaturaDTO> asignaturas) {
        this.asignaturas = asignaturas;
    }

    private String titulacion;
    private List<AsignaturaDTO> asignaturas;
}
