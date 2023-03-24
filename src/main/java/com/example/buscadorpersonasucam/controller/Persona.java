package com.example.buscadorpersonasucam.controller;

import java.io.Serializable;

public class Persona implements Serializable {
    private int id;
    private String nombre;
    private String apellidos;
    private String departamento;
    private String imagen;

    public Persona(int id, String nombre, String apellidos) {
        this.id=id;
        this.nombre=nombre;
        this.apellidos=apellidos;
    }

    public Persona() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
