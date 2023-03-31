package com.example.buscadorpersonasucam.beans.DTO;

import java.io.Serializable;

public class DireccionElasticDTO implements Serializable {
    public DireccionElasticDTO() {
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    private String calle;
    private String codigoPostal;
    private String localidad;
    private String provincia;
}
