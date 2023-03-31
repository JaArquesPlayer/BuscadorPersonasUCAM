package com.example.buscadorpersonasucam.beans.DTO;

import java.io.Serializable;
import java.util.List;

public class PublicacionDTO implements Serializable {
    public String getTipoPublicacion() {
        return tipoPublicacion;
    }

    public void setTipoPublicacion(String tipoPublicacion) {
        this.tipoPublicacion = tipoPublicacion;
    }

    public List<AutorDTO> getAutores() {
        return autores;
    }

    public void setAutores(List<AutorDTO> autores) {
        this.autores = autores;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getFuenteImpacto() {
        return fuenteImpacto;
    }

    public void setFuenteImpacto(String fuenteImpacto) {
        this.fuenteImpacto = fuenteImpacto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRevista() {
        return revista;
    }

    public void setRevista(String revista) {
        this.revista = revista;
    }

    public String getVolumenCapitulo() {
        return volumenCapitulo;
    }

    public void setVolumenCapitulo(String volumenCapitulo) {
        this.volumenCapitulo = volumenCapitulo;
    }

    public String getPaginaInicio() {
        return paginaInicio;
    }

    public void setPaginaInicio(String paginaInicio) {
        this.paginaInicio = paginaInicio;
    }

    public String getPaginaFinal() {
        return paginaFinal;
    }

    public void setPaginaFinal(String paginaFinal) {
        this.paginaFinal = paginaFinal;
    }

    public Double getPosicion() {
        return posicion;
    }

    public void setPosicion(Double posicion) {
        this.posicion = posicion;
    }

    private String tipoPublicacion;
    private List<AutorDTO> autores;
    private String titulo;
    private String editorial;
    private String fuenteImpacto;
    private String url;
    private String revista;
    private String volumenCapitulo;
    private String paginaInicio;
    private String paginaFinal;
    private Double posicion;
}
