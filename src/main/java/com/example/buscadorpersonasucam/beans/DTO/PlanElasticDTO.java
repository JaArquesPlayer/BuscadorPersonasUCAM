package com.example.buscadorpersonasucam.beans.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlanElasticDTO implements Serializable {
    public PlanElasticDTO() {
        this.asignaturas = new ArrayList<AsignaturaElasticDTO>();
    }

    public PlanElasticDTO(Long id, String nombrePlan, String nombreEstudio, String edicion, String tipoEstudio) {
        this.id = id;
        this.nombrePlan = nombrePlan;
        this.nombreEstudio = nombreEstudio;
        this.edicion = edicion;
        this.tipoEstudio = tipoEstudio;
        this.asignaturas = new ArrayList<AsignaturaElasticDTO>();
    }

    public PlanElasticDTO(Long id, Long idEstudio, String nombrePlan, String nombreEstudio, String edicion, String tipoEstudio) {
        this.id = id;
        this.idEstudio = idEstudio;
        this.nombrePlan = nombrePlan;
        this.nombreEstudio = nombreEstudio;
        this.edicion = edicion;
        this.tipoEstudio = tipoEstudio;
        this.asignaturas = new ArrayList<AsignaturaElasticDTO>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEstudio() {
        return idEstudio;
    }

    public void setIdEstudio(Long idEstudio) {
        this.idEstudio = idEstudio;
    }

    public String getNombrePlan() {
        return nombrePlan;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    public String getNombreEstudio() {
        return nombreEstudio;
    }

    public void setNombreEstudio(String nombreEstudio) {
        this.nombreEstudio = nombreEstudio;
    }

    public List<AsignaturaElasticDTO> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(List<AsignaturaElasticDTO> asignaturas) {
        this.asignaturas = asignaturas;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public String getTipoEstudio() {
        return tipoEstudio;
    }

    public void setTipoEstudio(String tipoEstudio) {
        this.tipoEstudio = tipoEstudio;
    }

    private Long id;
    private Long idEstudio;
    private String nombrePlan;
    private String nombreEstudio;
    private String edicion;
    private List<AsignaturaElasticDTO> asignaturas;
    private String tipoEstudio;
}
