package com.example.buscadorpersonasucam.beans.DTO;

import java.io.Serializable;
import java.util.List;

public class ResultadoBusquedaDTO implements Serializable {
    public ResultadoBusquedaDTO() {
    }

    public ResultadoBusquedaDTO(List<PersonaDTO> listaPersonas, List<PublicacionDTO> listaPublicaciones, Boolean resultadosProtegidos, int pageNumber, int pageSize, int totalItems, int totalPages) {
        this.listaPersonas = listaPersonas;
        this.listaPublicaciones = listaPublicaciones;
        this.resultadosProtegidos = resultadosProtegidos;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public List<PersonaDTO> getListaPersonas() {
        return listaPersonas;
    }

    public void setListaPersonas(List<PersonaDTO> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    public List<PublicacionDTO> getListaPublicaciones() {
        return listaPublicaciones;
    }

    public void setListaPublicaciones(List<PublicacionDTO> listaPublicaciones) {
        this.listaPublicaciones = listaPublicaciones;
    }

    public Boolean getResultadosProtegidos() {
        return resultadosProtegidos;
    }

    public void setResultadosProtegidos(Boolean resultadosProtegidos) {
        this.resultadosProtegidos = resultadosProtegidos;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    private List<PersonaDTO> listaPersonas;
    private List<PublicacionDTO> listaPublicaciones;
    private Boolean resultadosProtegidos;
    private int pageNumber;
    private int pageSize;
    private int totalItems;
    private int totalPages;
}
