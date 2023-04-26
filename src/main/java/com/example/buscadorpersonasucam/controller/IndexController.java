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

        PersonaElastic personaEncontrada = getPersonaById(id);
        PersonaDTO personaEncontradaDTO = new PersonaDTO();
        List<ProyectoDTO> proyectosCompetitivosDTO = new ArrayList<>();
        List<ProyectoDTO> proyectosNoCompetitivosDTO = new ArrayList<>();

        if (personaEncontrada != null) {
            personaEncontradaDTO = personaEncontrada.toDTO();

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

        List<PersonaElastic> personasEncontradasDepartamento = getPersonasByDepartamento(personaEncontradaDTO.getUbicacion());
        List<PersonaDTO> personasEncontradasDepartamentoDTO = new ArrayList<>();

        if (personasEncontradasDepartamento != null) {
            for (int i=0; i<personasEncontradasDepartamento.size(); i++){
                PersonaDTO personaDTO = personasEncontradasDepartamento.get(i).toDTO();
                personasEncontradasDepartamentoDTO.add(personaDTO);
            }
        }

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

        List<PersonaElastic> personasEncontradas = new ArrayList<>();
        List<PersonaDTO> personasEncontradasDTO = new ArrayList<>();

        if (busqueda.length() >= 3){
            if (busqueda != null) {
                personasEncontradas = getPersonasByBusqueda(busqueda);
            }

            for (; cont<personasEncontradas.size(); cont++){
                PersonaDTO personaDTO = personasEncontradas.get(cont).toDTO();
                personasEncontradasDTO.add(personaDTO);

                if (personasEncontradasDTO.size() == 4){
                    break;
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

                if (publicacionesEncontradasDTO.size() == 4){
                    break;
                }
            }
        }

        if (publicacionesEncontradasDTO.isEmpty() && cont == 0){
            return "plantillas/sinResultado";
        }else{
            cont += 1;
            //todo
            logger.info(String.valueOf(cont));
            model.addAttribute("i", cont);
            model.addAttribute("publicacionesEncontradas", publicacionesEncontradasDTO);
            return "plantillas/publicaciones";
        }
    }

    @RequestMapping(value = "/searchNombres/{busqueda}")
    public String buscarNombresUrl(@PathVariable(required = false) String busqueda, Model model) throws IOException{

        if (busqueda.length() >= 3){
            List<PersonaElastic> personasEncontradas = new ArrayList<>();
            if (busqueda != null) {
                personasEncontradas = getPersonasByBusqueda(busqueda);
            }

            List<PersonaDTO> personasEncontradasDTO = new ArrayList<>();
            for (int i=0; i<personasEncontradas.size(); i++){
                PersonaDTO personaDTO = personasEncontradas.get(i).toDTO();
                personasEncontradasDTO.add(personaDTO);
                if (i >= 4){break;}
            }

            model.addAttribute("personasEncontradas", personasEncontradasDTO);
        }

        return "plantillas/nombres";
    }

    public String normalize(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+","");
    }

    public List<PersonaElastic> getPersonas() throws IOException {

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

        List<PersonaElastic> personasEncontradas = new ArrayList<>();

        for (int i=0; i< personas.size(); i++){
            personasEncontradas.add(personas.get(i));
        }
        return personasEncontradas;
    }

    public List<PersonaElastic> getPersonasByDepartamento(@NotNull String busqueda) throws IOException{

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

        List<PersonaElastic> personasEncontradas = new ArrayList<>();

        for (int i=0; i< personas.size(); i++){
            busqueda = normalize(busqueda).toLowerCase();

            for(int z=0; z<personas.get(i).getDepartamentos().size(); z++){
                String departamento = normalize(personas.get(i).getDepartamentos().get(z).getNombre()).toLowerCase();

                if (departamento.contains(busqueda)){
                    personasEncontradas.add(personas.get(i));
                }
            }
        }

        return personasEncontradas;
    }

    public PersonaElastic getPersonaById(Integer id) throws IOException {

        String result = String.valueOf(elasticsearchRepository.searchById(id));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(result);
        JsonNode hitsNode = rootNode.get("hits").get("hits");
        JsonNode hit = hitsNode.get(0);

        JsonNode sourceNode = hit.get("_source");
        PersonaElastic personaEncontrada = mapper.treeToValue(sourceNode, PersonaElastic.class);

        return personaEncontrada;
    }

    public List<PersonaElastic> getPersonasByBusqueda(@NotNull String busqueda) throws IOException {

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

        List<PersonaElastic> personasEncontradas = new ArrayList<>();

        for (int i=0; i< personas.size(); i++){
            busqueda = normalize(busqueda).toLowerCase();
            String nombre_completo = personas.get(i).getNombre_completo();

            if (normalize(nombre_completo).toLowerCase().contains(busqueda)){
                personasEncontradas.add(personas.get(i));
            }
        }
        return personasEncontradas;
    }

    public List<DepartamentoDTO> getDepartamentosByBusqueda(@NotNull String busqueda) throws IOException{
        busqueda = normalize(busqueda).toLowerCase();

        List<PersonaElastic> personasEncontradas = new ArrayList<>();
        List<PersonaElastic> personasEncontradasPorDepartamento = new ArrayList<>();
        List<String> departamentosEncontrados = new ArrayList<>();

        List<PersonaElastic> personasEncontradasDepartamento;
        List<DepartamentoDTO> departamentosEncontradosAgrupados = new ArrayList<>();

        if (busqueda.length() >= 3){
            if (busqueda != null) {
                personasEncontradas = getPersonasByBusqueda(busqueda);
                personasEncontradasPorDepartamento = getPersonasByDepartamento(busqueda);
            }

            for (int i=0; i<personasEncontradas.size(); i++){
                PersonaDTO personaDTO = personasEncontradas.get(i).toDTO();

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
                PersonaDTO personaDTO = personasEncontradasPorDepartamento.get(i).toDTO();

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
                    personasDelDepartamento.add(personasEncontradasDepartamento.get(z).toDTO());

                }else if (normalize(personasEncontradasDepartamento.get(z).getNombre_completo()).toLowerCase().contains(busqueda)){
                    personasDelDepartamento.add(personasEncontradasDepartamento.get(z).toDTO());
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
            List<PersonaElastic> personasEncontradas = getPersonas();

            for (int i=0; i<personasEncontradas.size(); i++){
                PersonaDTO personaDTO = personasEncontradas.get(i).toDTO();
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