package com.example.buscadorpersonasucam.controller;

import com.example.buscadorpersonasucam.beans.DTO.*;
import com.example.buscadorpersonasucam.database.entity.PersonaElastic;
import com.example.buscadorpersonasucam.repository.ElasticsearchRepository;

import com.example.buscadorpersonasucam.services.PersonaService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.WordUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Logger;

@Controller
public class IndexController {

    private final static Logger logger = Logger.getLogger("com.example.buscadorpersonasucam.controller.IndexController");

    private final PersonaService buscadorService;
    private final ElasticsearchRepository elasticsearchRepository;


    @Value("${meta_imagen_controller_call}")
    private String ruta_controller_imagen;

    public IndexController(PersonaService buscadorService, ElasticsearchRepository elasticsearchRepository) {
        this.buscadorService = buscadorService;
        this.elasticsearchRepository = elasticsearchRepository;
    }

    @RequestMapping(value = "/personas")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/perfil/{id}")
    public String perfil(Model model, @PathVariable int id) throws IOException {

        PersonaDTO personaEncontrada = getPersonaById(id);
        PersonaDTO personaEncontradaDTO = new PersonaDTO();

        if (personaEncontrada != null) {
            personaEncontradaDTO = personaEncontrada;
        }

        List<PersonaDTO> personasEncontradasDepartamentoDTO = getPersonasByDepartamento(personaEncontradaDTO.getUbicacion());

        model.addAttribute("persona", personaEncontradaDTO);
        model.addAttribute("personasEncontradasDepartamento", personasEncontradasDepartamentoDTO);
        model.addAttribute( "ruta_controller_imagen", ruta_controller_imagen);

        String cargos = " | ";
        for (int i=0; i<personaEncontradaDTO.getCargos().size(); i++){
            cargos += personaEncontradaDTO.getCargos().get(i).getNombre();

            if(i != (personaEncontradaDTO.getCargos().size() - 1)){
                cargos += " | ";
            }
        }
        model.addAttribute("cargos", cargos);

        int maxPublicaciones = (int) Math.ceil((double) personaEncontradaDTO.getPublicaciones().size() / 10);
        List<Integer> arrayPublicaciones = new ArrayList<>();
        for (int i=0; i<maxPublicaciones; i++) {
            arrayPublicaciones.add(i);
        }
        model.addAttribute("maxPagPublicaciones", arrayPublicaciones);

        List<ProyectoDTO> proyectosCompetitivosDTO = new ArrayList<>();
        if (personaEncontrada != null) {
            personaEncontradaDTO = personaEncontrada;

            if (personaEncontradaDTO.getProyectos() != null) {

                for (int i = 0; i < personaEncontradaDTO.getProyectos().size(); i++) {
                    if (personaEncontradaDTO.getProyectos().get(i).getTipoProyecto().equalsIgnoreCase("COMPETITIVO")) {
                        proyectosCompetitivosDTO.add(personaEncontradaDTO.getProyectos().get(i));

                    }
                }
            }
        }
        int maxProyectosComp = (int) Math.ceil((double) proyectosCompetitivosDTO.size() / 10);
        List<Integer> arrayProyectosComp = new ArrayList<>();
        for (int i=0; i<maxProyectosComp; i++) {
            arrayProyectosComp.add(i);
        }
        model.addAttribute("maxPagProyectosComp", arrayProyectosComp);

        List<ProyectoDTO> proyectosNoCompetitivosDTO = new ArrayList<>();
        if (personaEncontrada != null) {
            personaEncontradaDTO = personaEncontrada;

            if (personaEncontradaDTO.getProyectos() != null) {

                for (int i = 0; i < personaEncontradaDTO.getProyectos().size(); i++) {
                    if (personaEncontradaDTO.getProyectos().get(i).getTipoProyecto().equalsIgnoreCase("NO COMPETITIVO")) {
                        proyectosNoCompetitivosDTO.add(personaEncontradaDTO.getProyectos().get(i));
                    }
                }
            }
        }
        int maxProyectosNoComp = (int) Math.ceil((double) proyectosNoCompetitivosDTO.size() / 10);
        List<Integer> arrayProyectosNoComp = new ArrayList<>();
        for (int i=0; i<maxProyectosNoComp; i++) {
            arrayProyectosNoComp.add(i);
        }
        model.addAttribute("maxPagProyectosNoComp", arrayProyectosNoComp);
        return "perfil";
    }

    @RequestMapping(value = "/paginacionPerfilPublicaciones/{id}")
    public String recuperarPaginaPublicaciones(@PathVariable int id, Model model, @RequestParam("pagina") int pagina) throws IOException{

        List<PublicacionDTO> publicaciones = getPersonaById(id).getPublicaciones();

        Pageable pageable = PageRequest.of(pagina, 10);
        int startIndex = pageable.getPageNumber() * pageable.getPageSize();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), publicaciones.size());
        if (startIndex >= publicaciones.size()) {

        }

        List<PublicacionDTO> itemsForPage = publicaciones.subList(startIndex, endIndex);
        PageImpl<PublicacionDTO> paginaCompleta = new PageImpl<>(itemsForPage, pageable, publicaciones.size());
        int totalPages = (int) Math.ceil((double) publicaciones.size() / 10);

        model.addAttribute("max", (totalPages-1));
        model.addAttribute("publicaciones", paginaCompleta);

        return "plantillas/publicacionesPaginadas";
    }

    @RequestMapping(value = "/paginacionPerfilProyectosComp/{id}")
    public String recuperarPaginaProyectosComp(@PathVariable int id, Model model, @RequestParam("pagina") int pagina) throws IOException{

        List<ProyectoDTO> proyectosCompetitivosDTO = new ArrayList<>();
        PersonaDTO personaEncontrada = getPersonaById(id);
        PersonaDTO personaEncontradaDTO = new PersonaDTO();

        if (personaEncontrada != null) {
            personaEncontradaDTO = personaEncontrada;

            if (personaEncontradaDTO.getProyectos() != null) {

                for (int i = 0; i < personaEncontradaDTO.getProyectos().size(); i++) {
                    if (personaEncontradaDTO.getProyectos().get(i).getTipoProyecto().equalsIgnoreCase("COMPETITIVO")) {
                        proyectosCompetitivosDTO.add(personaEncontradaDTO.getProyectos().get(i));

                    }
                }
            }
        }

        Pageable pageable = PageRequest.of(pagina, 10);
        int startIndex = pageable.getPageNumber() * pageable.getPageSize();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), proyectosCompetitivosDTO.size());
        if (startIndex >= proyectosCompetitivosDTO.size()) {

        }

        List<ProyectoDTO> itemsForPage = proyectosCompetitivosDTO.subList(startIndex, endIndex);
        PageImpl<ProyectoDTO> paginaCompleta = new PageImpl<>(itemsForPage, pageable, proyectosCompetitivosDTO.size());
        int totalPages = (int) Math.ceil((double) proyectosCompetitivosDTO.size() / 10);

        model.addAttribute("max", (totalPages-1));
        model.addAttribute("proyectosCompetitivos", paginaCompleta);

        return "plantillas/proyectosCompPaginados";
    }

    @RequestMapping(value = "/paginacionPerfilProyectosNoComp/{id}")
    public String recuperarPaginaProyectosNoComp(@PathVariable int id, Model model, @RequestParam("pagina") int pagina) throws IOException{

        List<ProyectoDTO> proyectosNoCompetitivosDTO = new ArrayList<>();
        PersonaDTO personaEncontrada = getPersonaById(id);
        PersonaDTO personaEncontradaDTO = new PersonaDTO();

        if (personaEncontrada != null) {
            personaEncontradaDTO = personaEncontrada;

            if (personaEncontradaDTO.getProyectos() != null) {

                for (int i = 0; i < personaEncontradaDTO.getProyectos().size(); i++) {
                    if (personaEncontradaDTO.getProyectos().get(i).getTipoProyecto().equalsIgnoreCase("NO COMPETITIVO")) {
                        proyectosNoCompetitivosDTO.add(personaEncontradaDTO.getProyectos().get(i));
                    }
                }
            }
        }

        Pageable pageable = PageRequest.of(pagina, 10);
        int startIndex = pageable.getPageNumber() * pageable.getPageSize();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), proyectosNoCompetitivosDTO.size());
        if (startIndex >= proyectosNoCompetitivosDTO.size()) {

        }

        List<ProyectoDTO> itemsForPage = proyectosNoCompetitivosDTO.subList(startIndex, endIndex);
        PageImpl<ProyectoDTO> paginaCompleta = new PageImpl<>(itemsForPage, pageable, proyectosNoCompetitivosDTO.size());
        int totalPages = (int) Math.ceil((double) proyectosNoCompetitivosDTO.size() / 10);

        model.addAttribute("max", (totalPages-1));
        model.addAttribute("proyectosNoCompetitivos", paginaCompleta);

        return "plantillas/proyectosNoCompPaginados";
    }

    @RequestMapping(value = "/searchPersonal/")
    public String buscarPersonas(@RequestParam(name = "search") String busqueda, Model model) {
        model.addAttribute("nombre", busqueda);

        return "resultados";
    }

    @RequestMapping(value = "/searchPersonal/{busqueda}")
    public String buscarPersonasUrl(@PathVariable String busqueda, Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "6") int size, @RequestParam(required = false) Boolean primeraBusqueda, @RequestParam(defaultValue = "true") Boolean paginado) {

        ResultadoBusquedaDTO resultado = new ResultadoBusquedaDTO();
        if((busqueda != null)) {
            if ((busqueda.length() >= 3)) {
                Pageable requestedPage = PageRequest.of(page, size);
                busqueda = Normalizer.normalize(busqueda, Normalizer.Form.NFD);
                busqueda = busqueda.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
                resultado = buscadorService.getPersonas(busqueda, requestedPage);
            }
        }

        if (resultado == null && page == 0){
            return "plantillas/sinResultado";
        } else{
            page += 1;
            model.addAttribute("i", page);
            model.addAttribute("personasEncontradas", resultado.getListaPersonas());
            return "plantillas/personal";
        }
    }

    @RequestMapping(value = "/searchDepartamentos/{busqueda}")
    public String buscarDepartamentosUrl(@PathVariable String busqueda, Model model, @RequestParam("pagina") int pagina) throws IOException {

        List<DepartamentoDTO> departamentosEncontradosAgrupados = new ArrayList<>();
        PageImpl<DepartamentoDTO> paginaCompleta = null;

        if (busqueda.length() >= 3) {
            if (busqueda != null) {
                departamentosEncontradosAgrupados = getDepartamentosByBusqueda(busqueda);
            }

            Pageable pageable = PageRequest.of(pagina, 10);
            int startIndex = pageable.getPageNumber() * pageable.getPageSize();
            int endIndex = Math.min(startIndex + pageable.getPageSize(), departamentosEncontradosAgrupados.size());
            if (startIndex >= departamentosEncontradosAgrupados.size()) {
                return "plantillas/void";
            }

            List<DepartamentoDTO> itemsForPage = departamentosEncontradosAgrupados.subList(startIndex, endIndex);
            paginaCompleta = new PageImpl<>(itemsForPage, pageable, departamentosEncontradosAgrupados.size());
        }

        if (paginaCompleta.isEmpty() && pagina == 0) {
            return "plantillas/sinResultado";
        } else {
            pagina += 1;
            model.addAttribute("i", pagina);
            model.addAttribute("departamentosEncontrados", paginaCompleta);
            return "plantillas/departamentos";
        }
    }

    @RequestMapping(value = "/searchPublicaciones/{busqueda}")
    public String buscarPublicaciones(@PathVariable String busqueda, Model model, @RequestParam("pagina") int pagina) throws IOException {

        List<PublicacionDTO> publicaciones = new ArrayList<>();
        PageImpl<PublicacionDTO> paginaCompleta = null;

        if (busqueda.length() >= 3) {
            if (busqueda != null) {
                publicaciones = getPublicacionesByBusqueda(busqueda);
            }

            Pageable pageable = PageRequest.of(pagina, 10);
            int startIndex = pageable.getPageNumber() * pageable.getPageSize();
            int endIndex = Math.min(startIndex + pageable.getPageSize(), publicaciones.size());
            if (startIndex >= publicaciones.size()) {
                return "plantillas/void";
            }

            List<PublicacionDTO> itemsForPage = publicaciones.subList(startIndex, endIndex);
            paginaCompleta = new PageImpl<>(itemsForPage, pageable, publicaciones.size());
        }

        if (paginaCompleta.isEmpty() && pagina == 0) {
            return "plantillas/sinResultado";
        } else {
            pagina += 1;
            model.addAttribute("i", pagina);
            model.addAttribute("publicacionesEncontradas", paginaCompleta);
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

        List<PersonaDTO> personasEncontradasDTO;

        if((busqueda != null)) {
            if (busqueda.length() >= 3) {
                busqueda = Normalizer.normalize(busqueda, Normalizer.Form.NFD);
                busqueda = busqueda.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
                personasEncontradasDTO = buscadorService.getAllPersonasByNombre(busqueda);

                if (personasEncontradasDTO.size() > 5){
                    model.addAttribute("personasEncontradas", personasEncontradasDTO.subList(0, 4));
                }else{
                    model.addAttribute("personasEncontradas", personasEncontradasDTO);
                }
            }
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

    public List<PersonaDTO> getPersonasByBusquedaSized(@NotNull String busqueda, Pageable p, boolean resultadosPaginados, boolean paginado) throws IOException {

        busqueda = normalize(busqueda);
        SearchPage<PersonaElastic> result = elasticsearchRepository.busquedaResultados(busqueda, p, resultadosPaginados, paginado);

        List<PersonaDTO> personas = new ArrayList<>();
        for (SearchHit<PersonaElastic> searchHit : result.getSearchHits()) {
            PersonaElastic personaElastic = searchHit.getContent();
            PersonaDTO personaDTO = personaElastic.toDTO();
            personas.add(personaDTO);
        }

        return personas;
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
                busqueda = Normalizer.normalize(busqueda, Normalizer.Form.NFD);
                busqueda = busqueda.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
                personasEncontradas = buscadorService.getAllPersonasByNombre(busqueda);
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

            if (!departamentoDTO.getPersonas().isEmpty()){
                departamentosEncontradosAgrupados.add(departamentoDTO);
            }
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