package com.example.buscadorpersonasucam.database.entity;

import com.example.buscadorpersonasucam.beans.DTO.AutorDTO;
import com.example.buscadorpersonasucam.beans.DTO.PublicacionDTO;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Entity
@Document(indexName = "alias_personas")
public class PublicacionElastic implements Serializable {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoPublicacion() {
        return tipo;
    }

    public void setTipoPublicacion(String tipo) {
        this.tipo = tipo;
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
        return fuente_impacto;
    }

    public void setFuenteImpacto(String fuente_impacto) {
        this.fuente_impacto = fuente_impacto;
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
        return volumen_capitulo;
    }

    public void setVolumenCapitulo(String volumen_capitulo) {
        this.volumen_capitulo = volumen_capitulo;
    }

    public String getPaginaInicio() {
        return pagina_inicio;
    }

    public void setPaginaInicio(String pagina_inicio) {
        this.pagina_inicio = pagina_inicio;
    }

    public String getPaginaFinal() {
        return pagina_final;
    }

    public void setPaginaFinal(String pagina_final) {
        this.pagina_final = pagina_final;
    }

    public Double getPosicion() {
        return posicion;
    }

    public void setPosicion(Double posicion) {
        this.posicion = posicion;
    }

    @Id
    private String id;
    @Field(name = "tipo")
    private String tipo;
    @Field(name = "autores")
    private List<AutorDTO> autores;
    @Field(name = "publicaciones.titulo")
    private String titulo;
    @Field(name = "editorial")
    private String editorial;
    @Field(name = "fuente_impacto")
    private String fuente_impacto;
    @Field(name = "url")
    private String url;
    @Field(name = "revista")
    private String revista;
    @Field(name = "volumen_capitulo")
    private String volumen_capitulo;
    @Field(name = "pagina_inicio")
    private String pagina_inicio;
    @Field(name = "pagina_final")
    private String pagina_final;
    @Field(name = "posicion", type = FieldType.Double)
    private Double posicion;

    public PublicacionDTO toDTO(){
        PublicacionDTO publicacionDTO = new PublicacionDTO();

        publicacionDTO.setAutores(this.autores);
        publicacionDTO.setEditorial(this.editorial);
        publicacionDTO.setTipoPublicacion(this.tipo);
        publicacionDTO.setPosicion(this.posicion);
        publicacionDTO.setRevista(this.revista);
        publicacionDTO.setFuenteImpacto(this.fuente_impacto);
        publicacionDTO.setPaginaInicio(this.pagina_inicio);
        publicacionDTO.setPaginaFinal(this.pagina_final);
        publicacionDTO.setVolumenCapitulo(this.volumen_capitulo);
        publicacionDTO.setUrl(this.url);

        return publicacionDTO;
    }
}
