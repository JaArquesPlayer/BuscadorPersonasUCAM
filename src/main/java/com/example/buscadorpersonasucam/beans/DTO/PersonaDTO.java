package com.example.buscadorpersonasucam.beans.DTO;

import java.io.Serializable;
import java.util.List;

public class PersonaDTO implements Serializable {

    public PersonaDTO() {
    }

    private int id;
    private String nombre_completo;
    private String nombre_mostrar;
    private List<String> correos_institucionales;
    private List<String> correos_personales;
    private List<String> telefonos;
    private String extension;
    private String instagram;
    private String linkedin;
    private String twitter;
    private String ubicacion;
    private Byte[] foto;

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

    public String getNombre_mostrar() {
        return nombre_mostrar;
    }

    public void setNombre_mostrar(String nombre_mostrar) {
        this.nombre_mostrar = nombre_mostrar;
    }

    public List<String> getCorreos_institucionales() {
        return correos_institucionales;
    }

    public void setCorreos_institucionales(List<String> correos_institucionales) {
        this.correos_institucionales = correos_institucionales;
    }

    public List<String> getCorreos_personales() {
        return correos_personales;
    }

    public void setCorreos_personales(List<String> correos_personales) {
        this.correos_personales = correos_personales;
    }

    public List<String> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<String> telefonos) {
        this.telefonos = telefonos;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Byte[] getFoto() {
        return foto;
    }

    public void setFoto(Byte[] foto) {
        this.foto = foto;
    }
}