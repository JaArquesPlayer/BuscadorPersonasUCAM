package com.example.buscadorpersonasucam.beans.DTO;

import java.io.Serializable;

public class GrupoInvestigacionDTO implements Serializable {
    public GrupoInvestigacionDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAliasWeb() {
        return aliasWeb;
    }

    public void setAliasWeb(String aliasWeb) {
        this.aliasWeb = aliasWeb;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String nombre;
    private String aliasWeb;
    private String url;
}
