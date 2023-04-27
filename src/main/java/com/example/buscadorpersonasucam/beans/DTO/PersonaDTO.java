package com.example.buscadorpersonasucam.beans.DTO;

import java.io.Serializable;
import java.util.List;

public class PersonaDTO implements Serializable {

    public PersonaDTO() {
    }

    private Long id;
    private String nombre_completo;
    private String nombre_mostrar;
    private List<String> areas_conocimiento;
    private List<String> correos_institucionales;
    private List<String> correos_personales;
    private List<String> telefonos;
    private List<String> formacion;
    private String extension;
    private String web;
    private String sexo;
    private String instagram;
    private String linkedin;
    private String twitter;
    private String ubicacion;
    private String sobre_mi;
    private String frase;
    private String autor_frase;
    private List<CargoElasticDTO> cargos;
    private List<PublicacionDTO> publicaciones;
    private List<ProyectoDTO> proyectos;
    private List<PlanElasticDTO> titulaciones_profesor;
    private List<PlanElasticDTO> titulaciones_alumno;
    private List<DepartamentoElasticDTO> departamentos;
    private List<DocenciaDTO> docencia;
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

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
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

    public List<String> getFormacion() {
        return formacion;
    }

    public void setFormacion(List<String> formacion) {
        this.formacion = formacion;
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

    public List<String> getAreas_conocimiento() {
        return areas_conocimiento;
    }

    public void setAreas_conocimiento(List<String> areas_conocimiento) {
        this.areas_conocimiento = areas_conocimiento;
    }

    public List<PublicacionDTO> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(List<PublicacionDTO> publicaciones) {
        this.publicaciones = publicaciones;
    }

    public List<ProyectoDTO> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<ProyectoDTO> proyectos) {
        this.proyectos = proyectos;
    }

    public String getSobre_mi() {
        return sobre_mi;
    }

    public void setSobre_mi(String sobre_mi) {
        this.sobre_mi = sobre_mi;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    public String getAutor_frase() {
        return autor_frase;
    }

    public void setAutor_frase(String autor_frse) {
        this.autor_frase = autor_frse;
    }

    public List<DocenciaDTO> getDocencia() {
        return docencia;
    }

    public void setDocencia(List<DocenciaDTO> docencia) {
        this.docencia = docencia;
    }

    public List<DepartamentoElasticDTO> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<DepartamentoElasticDTO> departamentos) {
        this.departamentos = departamentos;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}