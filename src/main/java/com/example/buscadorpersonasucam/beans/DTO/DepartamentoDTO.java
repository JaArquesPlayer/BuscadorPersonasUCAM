package com.example.buscadorpersonasucam.beans.DTO;

import java.io.Serializable;
import java.util.List;

public class DepartamentoDTO implements Serializable {
    public DepartamentoDTO() {}

    private String nombre;
    private List<PersonaDTO> personas;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<PersonaDTO> getPersonas() {
        return personas;
    }

    public void setPersonas(List<PersonaDTO> personas) {
        this.personas = personas;
    }
}
