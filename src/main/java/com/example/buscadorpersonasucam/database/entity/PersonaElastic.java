package com.example.buscadorpersonasucam.database.entity;

import com.example.buscadorpersonasucam.beans.DTO.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document(indexName = "alias_personas", createIndex = false)
public class PersonaElastic implements Serializable {

    private Long id;
    @Field(name = "id_ucam")
    private Long idUcam;
    @Field(name = "fecha_actualizacion", type = FieldType.Date)
    private Long fechaActualizacion;
    @Field(name = "nombre")
    private String nombre;
    @Field(name = "apellido1")
    private String apellido1;
    @Field(name = "apellido2")
    private String apellido2;
    @Field(name = "fecha_nacimiento", type = FieldType.Date)
    private Long fechaNacimiento;
    @Field(name = "numeroDocumento")
    private String docId;
    @Field(name = "tipo_documento")
    private String tipoDocumento;
    @Field(name = "nombre_mostrar")
    private String nombreMostrar;
    @Field(name = "nombre_completo")
    private String nombreCompleto;
    @Field(name = "origenes")
    private List origenes;
    @Field(name = "colectivo")
    private List colectivo;
    @Field(name = "privacidad")
    private String privacidad;
    @Field(name = "titulaciones_alumno")
    private List<PlanElasticDTO> titulacionesAlumno;
    @Field(name = "titulaciones_profesor")
    private List<PlanElasticDTO> titulacionesProfesor;
    @Field(name = "niu")
    private Long niu;
    @Field(name = "codigo_asistencia")
    private Long codigoAsistencia;
    @Field(name = "fp_id")
    private String fpId;
    @Field(name = "telefonos")
    private List<String> telefonos;
    @Field(name = "correos_personales")
    private List<String> correosPersonales;
    @Field(name = "correos_institucionales")
    private List<String> correosInstitucionales;
    @Field(name = "codigo_postal")
    private String codigoPostal;
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
    private String aliasWeb;
    @Field(name = "formacion")
    private List<String> formacion;
    @Field(name = "docencia")
    private List<DocenciaDTO> docencia;
    @Field(name = "frase")
    private String frase;
    @Field(name = "autor_frase")
    private String autorFrase;
    @Field(name = "sobre_mi")
    private String sobreMi;
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
    private List<GrupoInvestigacionDTO> gruposInvestigacion;
    @Field(name = "publicaciones")
    private List<PublicacionDTO> publicaciones;
    @Field(name = "proyectos")
    private List<ProyectoDTO> proyectos;
    @Field(name = "foto", index = false, type = FieldType.Binary)
    private Byte[] foto;
    @Field(name = "visitas_buscador")
    private Long visitasBuscador;
    @Field(name = "telefono_ucam")
    private String telefonoUcam;
    @Field(name = "areas_conocimiento")
    private List<String> areasConocimiento;
    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        this.idUcam = id;
    }

    public Long getIdUcam() {
        return idUcam;
    }

    public void setIdUcam(Long idUcam) {
        this.idUcam = idUcam;
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

    public Long getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Long fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaNacimientoFormateada() {
        final PersonaElastic thisInstance = this;
        if (thisInstance != null && thisInstance.getFechaNacimiento() != null) {
            return new Date(thisInstance.getFechaNacimiento());
        } else {
            return null;
        }
    }

    public void setFechaNacimientoFormateada(Date date) {
        this.fechaNacimiento = date.getTime();
    }

    public String getNombreMostrar() {
        return nombreMostrar;
    }

    public void setNombreMostrar(String nombreMostrar) {
        this.nombreMostrar = nombreMostrar;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
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

    public List<PlanElasticDTO> getTitulacionesAlumno() {
        return titulacionesAlumno;
    }

    public void setTitulacionesAlumno(List<PlanElasticDTO> titulacionesAlumno) {
        this.titulacionesAlumno = titulacionesAlumno;
    }

    public List<PlanElasticDTO> getTitulacionesProfesor() {
        return titulacionesProfesor;
    }

    public void setTitulacionesProfesor(List<PlanElasticDTO> titulacionesProfesor) {
        this.titulacionesProfesor = titulacionesProfesor;
    }

    public Long getNiu() {
        return niu;
    }

    public void setNiu(Long niu) {
        this.niu = niu;
    }

    public Long getCodigoAsistencia() {
        return codigoAsistencia;
    }

    public void setCodigoAsistencia(Long codigoAsistencia) {
        this.codigoAsistencia = codigoAsistencia;
    }

    public String getFpId() {
        return fpId;
    }

    public void setFpId(String fpId) {
        this.fpId = fpId;
    }

    public List<String> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<String> telefonos) {
        this.telefonos = telefonos;
    }

    public List<String> getCorreosPersonales() {
        return correosPersonales;
    }

    public void setCorreosPersonales(List<String> correosPersonales) {
        this.correosPersonales = correosPersonales;
    }

    public List<String> getCorreosInstitucionales() {
        return correosInstitucionales;
    }

    public void setCorreosInstitucionales(List<String> correosInstitucionales) {
        this.correosInstitucionales = correosInstitucionales;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
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

    public Byte[] getFoto() {
        return foto;
    }

    public void setFoto(Byte[] foto) {
        this.foto = foto;
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

    public String getAutorFrase() {
        return autorFrase;
    }

    public void setAutorFrase(String autorFrase) {
        this.autorFrase = autorFrase;
    }

    public String getSobreMi() {
        return sobreMi;
    }

    public void setSobreMi(String sobreMi) {
        this.sobreMi = sobreMi;
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

    public List<GrupoInvestigacionDTO> getGruposInvestigacion() {
        return gruposInvestigacion;
    }

    public void setGruposInvestigacion(List<GrupoInvestigacionDTO> gruposInvestigacion) {
        this.gruposInvestigacion = gruposInvestigacion;
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

    public String getPrivacidad() {
        return privacidad;
    }

    public void setPrivacidad(String privacidad) {
        this.privacidad = privacidad;
    }

    public String getAliasWeb() {
        return aliasWeb;
    }

    public void setAliasWeb(String aliasWeb) {
        this.aliasWeb = aliasWeb;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Long getVisitasBuscador() {
        return visitasBuscador;
    }

    public void setVisitasBuscador(Long visitasBuscador) {
        this.visitasBuscador = visitasBuscador;
    }

    public Long getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Long fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getTelefonoUcam() {
        return telefonoUcam;
    }

    public void setTelefonoUcam(String telefonoUcam) {
        this.telefonoUcam = telefonoUcam;
    }

    public List<String> getAreasConocimiento() {
        return areasConocimiento;
    }

    public void setAreasConocimiento(List<String> areasConocimiento) {
        this.areasConocimiento = areasConocimiento;
    }
}
