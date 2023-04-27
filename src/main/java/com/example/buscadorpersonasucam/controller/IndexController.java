package com.example.buscadorpersonasucam.controller;

import com.example.buscadorpersonasucam.beans.DTO.*;
import com.example.buscadorpersonasucam.database.entity.PersonaElastic;
import com.example.buscadorpersonasucam.repository.ElasticsearchRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.WordUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Logger;

@Controller
public class IndexController {

    private final static Logger logger = Logger.getLogger("com.example.buscadorpersonasucam.controller.IndexController");

    private final ElasticsearchRepository elasticsearchRepository;

    public IndexController(ElasticsearchRepository elasticsearchRepository) {
        this.elasticsearchRepository = elasticsearchRepository;
    }

    @Value("${meta_imagen_controller_call}")
    private String ruta_controller_imagen;

    @RequestMapping(value = "/personas")
    public String index() {
        return "index2";
    }

    @RequestMapping(value = "/perfil/{id}")
    public String perfil(Model model, @PathVariable int id) throws IOException {

        PersonaDTO personaEncontrada = getPersonaById(id);
        PersonaDTO personaEncontradaDTO = new PersonaDTO();
        List<ProyectoDTO> proyectosCompetitivosDTO = new ArrayList<>();
        List<ProyectoDTO> proyectosNoCompetitivosDTO = new ArrayList<>();

        if (personaEncontrada != null) {
            personaEncontradaDTO = personaEncontrada;

            if (personaEncontradaDTO.getProyectos() != null) {

                for (int i = 0; i < personaEncontradaDTO.getProyectos().size(); i++) {
                    if (personaEncontradaDTO.getProyectos().get(i).getTipoProyecto().equalsIgnoreCase("COMPETITIVO")) {
                        proyectosCompetitivosDTO.add(personaEncontradaDTO.getProyectos().get(i));

                    } else if (personaEncontradaDTO.getProyectos().get(i).getTipoProyecto().equalsIgnoreCase("NO COMPETITIVO")) {
                        proyectosNoCompetitivosDTO.add(personaEncontradaDTO.getProyectos().get(i));
                    }
                }
            }
        }

        List<PersonaDTO> personasEncontradasDepartamentoDTO = getPersonasByDepartamento(personaEncontradaDTO.getUbicacion());

        model.addAttribute("persona", personaEncontradaDTO);
        model.addAttribute("personasEncontradasDepartamento", personasEncontradasDepartamentoDTO);
        model.addAttribute("proyectosCompetitivos", proyectosCompetitivosDTO);
        model.addAttribute("proyectosNoCompetitivos", proyectosNoCompetitivosDTO);
        model.addAttribute( "ruta_controller_imagen", ruta_controller_imagen);

        String cargos = " | ";
        for (int i=0; i<personaEncontradaDTO.getCargos().size(); i++){
            cargos += personaEncontradaDTO.getCargos().get(i).getNombre();

            if(i != (personaEncontradaDTO.getCargos().size() - 1)){
                cargos += " | ";
            }
        }

        model.addAttribute("cargos", cargos);
        return "perfil";
    }

    @RequestMapping(value = "/searchPersonal/")
    public String buscarPersonas( @RequestParam(name = "search") String busqueda, Model model) {
        model.addAttribute("nombre", busqueda);

        return "resultados";
    }

    @RequestMapping(value = "/searchPersonal/{busqueda}")
    public String buscarPersonasUrl(@PathVariable String busqueda, Model model, @RequestParam("variable") int cont) throws IOException{

        List<PersonaDTO> personasEncontradas = new ArrayList<>();
        List<PersonaDTO> personasEncontradasDTO = new ArrayList<>();

        if (busqueda.length() >= 3){
            if (busqueda != null) {
                personasEncontradas = getPersonasByBusqueda(busqueda);

                if (!personasEncontradas.isEmpty()) {
                    for (; cont < personasEncontradas.size(); cont++) {
                        PersonaDTO personaDTO = personasEncontradas.get(cont);
                        personasEncontradasDTO.add(personaDTO);

                        if (personasEncontradasDTO.size() == 8) {
                            break;
                        }
                    }
                }else {
                    busqueda = normalize(busqueda).toLowerCase();
                    personasEncontradas = getPersonas();

                    for (; cont<personasEncontradas.size(); cont++){
                        PersonaDTO personaDTO = personasEncontradas.get(cont);
                        boolean encontrado = false;

                        if (normalize(personaDTO.getNombre_completo()).toLowerCase().contains(busqueda)){
                            encontrado = true;

                        }else if (normalize(personaDTO.getExtension()).toLowerCase().contains(busqueda)){
                            encontrado = true;

                        }else {
                            if (encontrado == false){
                                for (int i = 0; i < personaDTO.getCorreos_institucionales().size(); i++) {
                                    String correo_institucional = normalize(personaDTO.getCorreos_institucionales().get(i)).toLowerCase();

                                    if (correo_institucional.contains(busqueda)) {
                                        encontrado = true;
                                        break;
                                    }
                                }
                            }
                            if (encontrado == false){
                                for (int i = 0; i < personaDTO.getDepartamentos().size(); i++) {
                                    String departamento = normalize(personaDTO.getDepartamentos().get(i).getNombre()).toLowerCase();

                                    if (departamento.contains(busqueda)) {
                                        encontrado = true;
                                        break;
                                    }
                                }
                            }
                            if (encontrado == false){
                                for (int i = 0; i < personaDTO.getAreas_conocimiento().size(); i++) {
                                    String areas_conocimiento = normalize(personaDTO.getAreas_conocimiento().get(i)).toLowerCase();

                                    if (areas_conocimiento.contains(busqueda)) {
                                        encontrado = true;
                                        break;
                                    }
                                }
                            }

                        }

                        if (encontrado == true){
                            personasEncontradasDTO.add(personaDTO);
                        }
                        if (personasEncontradasDTO.size() == 4){
                            break;
                        }
                    }
                }
            }
        }


        if (personasEncontradasDTO.isEmpty() && cont == 0){
            return "plantillas/sinResultado";
        } else{
            cont += 1;
            model.addAttribute("i", cont);
            model.addAttribute("personasEncontradas", personasEncontradasDTO);
            return "plantillas/personal";
        }
    }

    @RequestMapping(value = "/searchDepartamentos/{busqueda}")
    public String buscarDepartamentosUrl(@PathVariable String busqueda, Model model, @RequestParam("variable") int cont) throws IOException{

        List<DepartamentoDTO> departamentosEncontradosAgrupados = new ArrayList<>();
        List<DepartamentoDTO> departamentosEncontrados = new ArrayList<>();

        if (busqueda.length() >= 3) {
            if (busqueda != null) {
                departamentosEncontradosAgrupados = getDepartamentosByBusqueda(busqueda);
            }

            for (; cont<departamentosEncontradosAgrupados.size(); cont++){
                DepartamentoDTO departamentoDTO = departamentosEncontradosAgrupados.get(cont);
                departamentosEncontrados.add(departamentoDTO);

                if (departamentosEncontrados.size() == 4){
                    break;
                }
            }
        }

        if (departamentosEncontrados.isEmpty() && cont == 0){
            return "plantillas/sinResultado";
        }else{
            cont += 1;
            model.addAttribute("i", cont);
            model.addAttribute("departamentosEncontrados", departamentosEncontrados);
            return "plantillas/departamentos";
        }
    }

    @RequestMapping(value = "/searchPublicaciones/{busqueda}")
    public String buscarPublicaciones(@PathVariable String busqueda, Model model, @RequestParam("variable") int cont) throws IOException{

        List<PublicacionDTO> publicacionesEncontradasDTO = new ArrayList<>();
        List<PublicacionDTO> publicaciones = new ArrayList<>();

        if (busqueda.length() >= 3) {
            if (busqueda != null) {
                publicaciones = getPublicacionesByBusqueda(busqueda);
            }

            for (; cont<publicaciones.size(); cont++){
                PublicacionDTO publicacionDTO = publicaciones.get(cont);
                publicacionesEncontradasDTO.add(publicacionDTO);

                if (publicacionesEncontradasDTO.size() == 10){
                    break;
                }
            }
        }

        if (publicacionesEncontradasDTO.isEmpty() && cont == 0){
            return "plantillas/sinResultado";
        }else{
            cont += 1;
            model.addAttribute("i", cont);
            model.addAttribute("publicacionesEncontradas", publicacionesEncontradasDTO);
            return "plantillas/publicaciones";
        }
    }

    @RequestMapping(value = "/departamento/{nombre}")
    public String departamento(Model model, @PathVariable String nombre) throws IOException {

        String departamentoNormalized = normalize(nombre).toLowerCase();
        List<PersonaDTO> personas = getPersonas();
        List<PersonaDTO> personasEncontradas = new ArrayList<>();

        for (int i=0; i<personas.size(); i++){
            boolean encontrado = false;
            PersonaDTO personaDTO = personas.get(i);

            if (normalize(personaDTO.getUbicacion()).toLowerCase().equalsIgnoreCase(departamentoNormalized)){
                encontrado = true;
            }else{
                for (int z=0; z<personaDTO.getDepartamentos().size(); z++){
                    String departametoPersona = normalize(personaDTO.getDepartamentos().get(z).getNombre()).toLowerCase();

                    if (departametoPersona.equalsIgnoreCase(departamentoNormalized)){
                        encontrado = true;
                        break;
                    }
                }
            }
            if (encontrado){
                personasEncontradas.add(personaDTO);
            }
        }

        model.addAttribute("nombreDepartamento", nombre);
        model.addAttribute("personasEncontradas", personasEncontradas);

        return "departamento";
    }

    @RequestMapping(value = "/searchNombres/{busqueda}")
    public String buscarNombresUrl(@PathVariable(required = false) String busqueda, Model model) throws IOException{

        if (busqueda.length() >= 3){
            List<PersonaDTO> personasEncontradas = new ArrayList<>();
            if (busqueda != null) {
                personasEncontradas = getPersonasByBusqueda(busqueda);
            }

            List<PersonaDTO> personasEncontradasDTO = new ArrayList<>();
            for (int i=0; i<personasEncontradas.size(); i++){
                PersonaDTO personaDTO = personasEncontradas.get(i);
                personasEncontradasDTO.add(personaDTO);
                if (i == 4){break;}
            }

            model.addAttribute("personasEncontradas", personasEncontradasDTO);
        }

        return "plantillas/nombres";
    }

    public String normalize(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+","");
    }

    public List<PersonaDTO> getPersonas() throws IOException {

        String result = String.valueOf(elasticsearchRepository.searchAll());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(result);
        JsonNode hitsNode = rootNode.get("hits").get("hits");
        List<PersonaElastic> personas = new ArrayList<>();

        for (JsonNode hit : hitsNode) {
            JsonNode sourceNode = hit.get("_source");
            PersonaElastic personaElastic = mapper.treeToValue(sourceNode, PersonaElastic.class);
            personas.add(personaElastic);
        }

        List<PersonaDTO> personasEncontradas = new ArrayList<>();

        for (int i=0; i< personas.size(); i++){
            personasEncontradas.add(personas.get(i).toDTO());
        }
        return personasEncontradas;
    }

    public List<PersonaDTO> getPersonasByDepartamento(@NotNull String busqueda) throws IOException{

        String result = String.valueOf(elasticsearchRepository.searchAll());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(result);
        JsonNode hitsNode = rootNode.get("hits").get("hits");
        List<PersonaElastic> personas = new ArrayList<>();

        for (JsonNode hit : hitsNode) {
            JsonNode sourceNode = hit.get("_source");
            PersonaElastic personaElastic = mapper.treeToValue(sourceNode, PersonaElastic.class);
            personas.add(personaElastic);
        }

        List<PersonaDTO> personasEncontradas = new ArrayList<>();

        for (int i=0; i< personas.size(); i++){
            busqueda = normalize(busqueda).toLowerCase();

            for(int z=0; z<personas.get(i).getDepartamentos().size(); z++){
                String departamento = normalize(personas.get(i).getDepartamentos().get(z).getNombre()).toLowerCase();

                if (departamento.contains(busqueda)){
                    personasEncontradas.add(personas.get(i).toDTO());
                }
            }
        }

        return personasEncontradas;
    }

    public PersonaDTO getPersonaById(Integer id) throws IOException {

        String result = String.valueOf(elasticsearchRepository.searchById(id));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(result);
        JsonNode hitsNode = rootNode.get("hits").get("hits");
        JsonNode hit = hitsNode.get(0);

        JsonNode sourceNode = hit.get("_source");
        PersonaElastic personaEncontrada = mapper.treeToValue(sourceNode, PersonaElastic.class);

        return personaEncontrada.toDTO();
    }

    public List<PersonaDTO> getPersonasByBusqueda(@NotNull String busqueda) throws IOException {

        String result = String.valueOf(elasticsearchRepository.searchAll());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(result);
        JsonNode hitsNode = rootNode.get("hits").get("hits");
        List<PersonaElastic> personas = new ArrayList<>();

        for (JsonNode hit : hitsNode) {
            JsonNode sourceNode = hit.get("_source");
            PersonaElastic personaElastic = mapper.treeToValue(sourceNode, PersonaElastic.class);
            personas.add(personaElastic);
        }

        List<PersonaDTO> personasEncontradas = new ArrayList<>();

        for (int i=0; i< personas.size(); i++){
            busqueda = normalize(busqueda).toLowerCase();
            String nombre_completo = personas.get(i).getNombre_completo();

            if (normalize(nombre_completo).toLowerCase().contains(busqueda)){
                personasEncontradas.add(personas.get(i).toDTO());
            }
        }
        return personasEncontradas;
    }

    public List<DepartamentoDTO> getDepartamentosByBusqueda(@NotNull String busqueda) throws IOException{
        busqueda = normalize(busqueda).toLowerCase();

        List<PersonaDTO> personasEncontradas = new ArrayList<>();
        List<PersonaDTO> personasEncontradasPorDepartamento = new ArrayList<>();
        List<String> departamentosEncontrados = new ArrayList<>();

        List<PersonaDTO> personasEncontradasDepartamento;
        List<DepartamentoDTO> departamentosEncontradosAgrupados = new ArrayList<>();

        if (busqueda.length() >= 3){
            if (busqueda != null) {
                personasEncontradas = getPersonasByBusqueda(busqueda);
                personasEncontradasPorDepartamento = getPersonasByDepartamento(busqueda);
            }

            for (int i=0; i<personasEncontradas.size(); i++){
                PersonaDTO personaDTO = personasEncontradas.get(i);

                for (int z=0; z<personaDTO.getDepartamentos().size(); z++){
                    boolean repetido = false;

                    for (int k=0; k<departamentosEncontrados.size(); k++){
                        if (departamentosEncontrados.get(k).equalsIgnoreCase(personaDTO.getDepartamentos().get(z).getNombre())){
                            repetido = true;
                        }
                    }

                    if (repetido == false){
                        departamentosEncontrados.add(personaDTO.getDepartamentos().get(z).getNombre());
                    }
                }
            }

            for (int i=0; i<personasEncontradasPorDepartamento.size(); i++){
                PersonaDTO personaDTO = personasEncontradasPorDepartamento.get(i);

                for (int z=0; z<personaDTO.getDepartamentos().size(); z++){
                    boolean repetido = false;

                    for (int k=0; k<departamentosEncontrados.size(); k++){
                        if (departamentosEncontrados.get(k).equalsIgnoreCase(personaDTO.getDepartamentos().get(z).getNombre())){
                            repetido = true;
                        }
                    }

                    if (repetido == false){
                        departamentosEncontrados.add(personaDTO.getDepartamentos().get(z).getNombre());
                    }
                }
            }
        }

        for (int i=0; i<departamentosEncontrados.size(); i++){
            List<PersonaDTO> personasDelDepartamento = new ArrayList<>();
            DepartamentoDTO departamentoDTO = new DepartamentoDTO();
            departamentoDTO.setNombre(WordUtils.capitalizeFully(departamentosEncontrados.get(i)));

            personasEncontradasDepartamento = getPersonasByDepartamento(departamentosEncontrados.get(i));
            for (int z=0; z<personasEncontradasDepartamento.size(); z++){

                if (personasEncontradas.size()==0){
                    personasDelDepartamento.add(personasEncontradasDepartamento.get(z));

                }else if (normalize(personasEncontradasDepartamento.get(z).getNombre_completo()).toLowerCase().contains(busqueda)){
                    personasDelDepartamento.add(personasEncontradasDepartamento.get(z));
                }
            }

            departamentoDTO.setPersonas(personasDelDepartamento);
            departamentosEncontradosAgrupados.add(departamentoDTO);
        }
        return departamentosEncontradosAgrupados;
    }

    public List<PublicacionDTO> getPublicacionesByBusqueda(@NotNull String busqueda) throws IOException{
        busqueda = normalize(busqueda).toLowerCase();
        List<PublicacionDTO> publicacionesEncontradasDTO = new ArrayList<>();

        if (busqueda.length() >= 3){
            List<PersonaDTO> personasEncontradas = getPersonas();

            for (int i=0; i<personasEncontradas.size(); i++){
                PersonaDTO personaDTO = personasEncontradas.get(i);
                List<PublicacionDTO> publicacionesTmpDTO = personaDTO.getPublicaciones();

                if (publicacionesTmpDTO != null) {
                    for (int z = 0; z < publicacionesTmpDTO.size(); z++) {
                        String titulo = normalize(publicacionesTmpDTO.get(z).getTitulo()).toLowerCase();

                        boolean autorEncontrado = false;
                        List<AutorDTO> autores = publicacionesTmpDTO.get(z).getAutores();

                        for (int k = 0; k < autores.size(); k++) {
                            String nombre = normalize(autores.get(k).getNombre()).toLowerCase();
                            String primerApellido = "";
                            String segundoApellido = "";
                            String nombreCompleto = "";

                            if (autores.get(k).getPrimerApellido() != null) {
                                primerApellido = normalize(autores.get(k).getPrimerApellido()).toLowerCase();
                            }
                            if (autores.get(k).getSegundoApellido() != null) {
                                segundoApellido = normalize(autores.get(k).getSegundoApellido()).toLowerCase();
                            }
                            if (autores.get(k).getNombreCompleto() != null) {
                                nombreCompleto = normalize(autores.get(k).getNombreCompleto()).toLowerCase();
                            }
                            if (nombre.contains(busqueda) || primerApellido.contains(busqueda) || segundoApellido.contains(busqueda) || nombreCompleto.contains(busqueda)) {
                                autorEncontrado = true;
                            }
                        }

                        if (autorEncontrado == true || titulo.contains(busqueda)) {
                            publicacionesEncontradasDTO.add(publicacionesTmpDTO.get(z));
                        }
                    }
                }
            }
        }
        return publicacionesEncontradasDTO;
    }
}