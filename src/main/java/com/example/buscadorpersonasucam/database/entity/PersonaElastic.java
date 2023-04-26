package com.example.buscadorpersonasucam.database.entity;

import com.example.buscadorpersonasucam.beans.DTO.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import org.apache.commons.text.WordUtils;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Entity
@Document(indexName = "alias_personas", createIndex = false)
public class PersonaElastic implements Serializable {

    @Id
    private Long id;
    @Field(name = "id_ucam")
    private Long id_ucam;
    @Field(name = "fecha_actualizacion", type = FieldType.Date)
    private Long fecha_actualizacion;
    @Field(name = "nombre")
    private String nombre;
    @Field(name = "apellido1")
    private String apellido1;
    @Field(name = "apellido2")
    private String apellido2;
    @Field(name = "fecha_nacimiento", type = FieldType.Date)
    private Long fecha_nacimiento;
    @Field(name = "numeroDocumento")
    private String numeroDocumento;
    @Field(name = "tipo_documento")
    private String tipo_documento;
    @Field(name = "nombre_mostrar")
    private String nombre_mostrar;
    @Field(name = "nombre_completo")
    private String nombre_completo;
    @Field(name = "origenes")
    private List origenes;
    @Field(name = "colectivo")
    private List colectivo;
    @Field(name = "privacidad")
    private String privacidad;
    @Field(name = "titulaciones_alumno")
    private List<PlanElasticDTO> titulaciones_alumno;
    @Field(name = "titulaciones_profesor")
    private List<PlanElasticDTO> titulaciones_profesor;
    @Field(name = "niu")
    private Long niu;
    @Field(name = "codigo_asistencia")
    private Long codigo_asistencia;
    @Field(name = "fp_id")
    private String fp_id;
    @Field(name = "telefonos")
    private List<String> telefonos;
    @Field(name = "correos_personales")
    private List<String> correos_personales;
    @Field(name = "correos_institucionales")
    private List<String> correos_institucionales;
    @Field(name = "codigo_postal")
    private String codigo_postal;
    @Field(name = "localidad")
    private String localidad;
    @Field(name = "provincia")
    private String provincia;
    @Field(name = "direccion")
    private String direccion;
    @Field(name = "sexo")
    private String sexo;
    @Field(name = "ubicacion")
    private String ubicacion;
    @Field(name = "cargos")
    private List<CargoElasticDTO> cargos;
    @Field(name = "departamentos")
    private List<DepartamentoElasticDTO> departamentos;
    @Field(name = "alias_web")
    private String alias_web;
    @Field(name = "formacion")
    private List<String> formacion;
    @Field(name = "docencia")
    private List<DocenciaDTO> docencia;
    @Field(name = "frase")
    private String frase;
    @Field(name = "autor_frase")
    private String autor_frase;
    @Field(name = "sobre_mi")
    private String sobre_mi;
    @Field(name = "extension")
    private String extension;
    @Field(name = "facebook")
    private String facebook;
    @Field(name = "instagram")
    private String instagram;
    @Field(name = "linkedin")
    private String linkedin;
    @Field(name = "twitter")
    private String twitter;
    @Field(name = "web")
    private String web;
    @Field(name = "youtube")
    private String youtube;
    @Field(name = "grupos_investigacion")
    private List<GrupoInvestigacionDTO> grupos_investigacion;
    @Field(name = "publicaciones")
    private List<PublicacionDTO> publicaciones;
    @Field(name = "proyectos")
    private List<ProyectoDTO> proyectos;
    @Field(name = "foto", index = false, type = FieldType.Binary)
    private byte[] foto;
    @Field(name = "visitas_buscador")
    private Long visitas_buscador;
    @Field(name = "telefono_ucam")
    private String telefono_ucam;
    @Field(name = "areas_conocimiento")
    private List<String> areas_conocimiento;
    public String getDocId() {
        return numeroDocumento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        this.id_ucam = id;
    }

    public Long getId_ucam() {
        return id_ucam;
    }

    public void setId_ucam(Long id_ucam) {
        this.id_ucam = id_ucam;
    }

    public Long getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(Long fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public Long getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Long fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(String tipo_documento) {
        this.tipo_documento = tipo_documento;
    }

    public String getNombre_mostrar() {
        return nombre_mostrar;
    }

    public void setNombre_mostrar(String nombre_mostrar) {
        this.nombre_mostrar = nombre_mostrar;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public List getOrigenes() {
        return origenes;
    }

    public void setOrigenes(List origenes) {
        this.origenes = origenes;
    }

    public List getColectivo() {
        return colectivo;
    }

    public void setColectivo(List colectivo) {
        this.colectivo = colectivo;
    }

    public String getPrivacidad() {
        return privacidad;
    }

    public void setPrivacidad(String privacidad) {
        this.privacidad = privacidad;
    }

    public List<PlanElasticDTO> getTitulaciones_alumno() {
        return titulaciones_alumno;
    }

    public void setTitulaciones_alumno(List<PlanElasticDTO> titulaciones_alumno) {
        this.titulaciones_alumno = titulaciones_alumno;
    }

    public List<PlanElasticDTO> getTitulaciones_profesor() {
        return titulaciones_profesor;
    }

    public void setTitulaciones_profesor(List<PlanElasticDTO> titulaciones_profesor) {
        this.titulaciones_profesor = titulaciones_profesor;
    }

    public Long getNiu() {
        return niu;
    }

    public void setNiu(Long niu) {
        this.niu = niu;
    }

    public Long getCodigo_asistencia() {
        return codigo_asistencia;
    }

    public void setCodigo_asistencia(Long codigo_asistencia) {
        this.codigo_asistencia = codigo_asistencia;
    }

    public String getFp_id() {
        return fp_id;
    }

    public void setFp_id(String fp_id) {
        this.fp_id = fp_id;
    }

    public List<String> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<String> telefonos) {
        this.telefonos = telefonos;
    }

    public List<String> getCorreos_personales() {
        return correos_personales;
    }

    public void setCorreos_personales(List<String> correos_personales) {
        this.correos_personales = correos_personales;
    }

    public List<String> getCorreos_institucionales() {
        return correos_institucionales;
    }

    public void setCorreos_institucionales(List<String> correos_institucionales) {
        this.correos_institucionales = correos_institucionales;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<CargoElasticDTO> getCargos() {
        return cargos;
    }

    public void setCargos(List<CargoElasticDTO> cargos) {
        this.cargos = cargos;
    }

    public List<DepartamentoElasticDTO> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<DepartamentoElasticDTO> departamentos) {
        this.departamentos = departamentos;
    }

    public String getAlias_web() {
        return alias_web;
    }

    public void setAlias_web(String alias_web) {
        this.alias_web = alias_web;
    }

    public List<String> getFormacion() {
        return formacion;
    }

    public void setFormacion(List<String> formacion) {
        this.formacion = formacion;
    }

    public List<DocenciaDTO> getDocencia() {
        return docencia;
    }

    public void setDocencia(List<DocenciaDTO> docencia) {
        this.docencia = docencia;
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

    public void setAutor_frase(String autor_frase) {
        this.autor_frase = autor_frase;
    }

    public String getSobre_mi() {
        return sobre_mi;
    }

    public void setSobre_mi(String sobre_mi) {
        this.sobre_mi = sobre_mi;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
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

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public List<GrupoInvestigacionDTO> getGrupos_investigacion() {
        return grupos_investigacion;
    }

    public void setGrupos_investigacion(List<GrupoInvestigacionDTO> grupos_investigacion) {
        this.grupos_investigacion = grupos_investigacion;
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

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Long getVisitas_buscador() {
        return visitas_buscador;
    }

    public void setVisitas_buscador(Long visitas_buscador) {
        this.visitas_buscador = visitas_buscador;
    }

    public String getTelefono_ucam() {
        return telefono_ucam;
    }

    public void setTelefono_ucam(String telefono_ucam) {
        this.telefono_ucam = telefono_ucam;
    }

    public List<String> getAreas_conocimiento() {
        return areas_conocimiento;
    }

    public void setAreas_conocimiento(List<String> areas_conocimiento) {
        this.areas_conocimiento = areas_conocimiento;
    }

    public PersonaDTO toDTO(){
        PersonaDTO personaDTO = new PersonaDTO();

        personaDTO.setId(this.id);
        personaDTO.setNombre_completo(WordUtils.capitalizeFully(this.nombre_completo));
        personaDTO.setNombre_mostrar(WordUtils.capitalizeFully(this.nombre_mostrar));
        personaDTO.setCargos(this.cargos);
        personaDTO.setFoto(fromByteToBase64(this.foto));
        personaDTO.setExtension(this.extension);
        personaDTO.setLinkedin(this.linkedin);
        personaDTO.setInstagram(this.instagram);
        personaDTO.setTwitter(this.twitter);
        personaDTO.setCorreos_institucionales(this.correos_institucionales);
        personaDTO.setCorreos_personales(this.correos_personales);
        personaDTO.setTelefonos(this.telefonos);
        personaDTO.setUbicacion(WordUtils.capitalizeFully(this.ubicacion));
        personaDTO.setTitulaciones_alumno(this.titulaciones_alumno);
        personaDTO.setTitulaciones_profesor(this.titulaciones_profesor);
        personaDTO.setAreas_conocimiento(this.areas_conocimiento);
        personaDTO.setWeb(this.web);
        personaDTO.setPublicaciones(this.publicaciones);
        personaDTO.setProyectos(this.proyectos);
        personaDTO.setFormacion(this.formacion);
        personaDTO.setSobre_mi(this.sobre_mi);
        personaDTO.setFrase(this.frase);
        personaDTO.setAutor_frase(this.autor_frase);
        personaDTO.setDocencia(this.docencia);
        personaDTO.setGrupos_investigacion(this.grupos_investigacion);
        personaDTO.setDepartamentos(this.departamentos);
        personaDTO.setSexo(this.sexo);

        return personaDTO;
    }

    public String fromByteToBase64 (byte[] foto){
        String imagenBase64 = Base64.getEncoder().encodeToString(foto);
        return imagenBase64;
    }
}
