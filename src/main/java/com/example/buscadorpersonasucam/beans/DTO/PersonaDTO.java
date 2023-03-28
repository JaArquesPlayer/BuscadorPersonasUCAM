package com.example.buscadorpersonasucam.beans.DTO;

import java.io.Serializable;

public class PersonaDTO implements Serializable {

    public PersonaDTO() {
    }

    private int id;
    private String nombre_completo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }
}
