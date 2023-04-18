package com.example.buscadorpersonasucam.beans.DTO;

import java.io.Serializable;
import java.util.List;

public class PersonaDTO implements Serializable {

    public PersonaDTO() {
    }

    private Long id;
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
    private List<CargoElasticDTO> cargos;
    private List<PlanElasticDTO> titulaciones_profesor;
    private List<PlanElasticDTO> titulaciones_alumno;
    private List<GrupoInvestigacionDTO> grupos_investigacion;
    private String foto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<CargoElasticDTO> getCargos() {
        return cargos;
    }

    public void setCargos(List<CargoElasticDTO> cargos) {
        this.cargos = cargos;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<PlanElasticDTO> getTitulaciones_profesor() {
        return titulaciones_profesor;
    }

    public void setTitulaciones_profesor(List<PlanElasticDTO> titulaciones_profesor) {
        this.titulaciones_profesor = titulaciones_profesor;
    }

    public List<PlanElasticDTO> getTitulaciones_alumno() {
        return titulaciones_alumno;
    }

    public void setTitulaciones_alumno(List<PlanElasticDTO> titulaciones_alumno) {
        this.titulaciones_alumno = titulaciones_alumno;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<GrupoInvestigacionDTO> getGrupos_investigacion() {
        return grupos_investigacion;
    }

    public void setGrupos_investigacion(List<GrupoInvestigacionDTO> grupos_investigacion) {
        this.grupos_investigacion = grupos_investigacion;
    }
}