package com.example.buscadorpersonasucam.database.entity;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Document(indexName = "departamentos_test")
public class DepartamentoElastic implements Serializable {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreDepartamento() {
        return nombre_departamento;
    }

    public void setNombreDepartamento(String nombre_departamento) {
        this.nombre_departamento = nombre_departamento;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Id
    private Long id;
    @Field(name = "nombre_departamento")
    private String nombre_departamento;
    @Field(name = "ubicacion")
    private String ubicacion;
}
