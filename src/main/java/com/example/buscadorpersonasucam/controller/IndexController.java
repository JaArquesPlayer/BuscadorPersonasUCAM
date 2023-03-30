package com.example.buscadorpersonasucam.controller;

import com.example.buscadorpersonasucam.beans.DTO.PersonaDTO;
import com.example.buscadorpersonasucam.database.entity.PersonaElastic;
import com.example.buscadorpersonasucam.repository.ElasticsearchRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import java.util.logging.Logger;

@Controller
public class IndexController {

    private final static Logger logger = Logger.getLogger("com.example.buscadorpersonasucam.controller.IndexController");

    private final ElasticsearchRepository elasticsearchRepository;

    public IndexController(ElasticsearchRepository elasticsearchRepository) {
        this.elasticsearchRepository = elasticsearchRepository;
    }

    @RequestMapping(value = "/personas")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/perfil/{id}")
    public String perfil(Model model, @PathVariable int id) throws IOException {

        PersonaElastic personaEncontrada = getPersonaById(id);
        if (personaEncontrada != null) {
            PersonaDTO personaEncontradaDTO = personaEncontrada.toDTO();
            model.addAttribute("persona", personaEncontradaDTO);
        }
        return "profile";
    }

    @RequestMapping(value = "/searchPersonal/{busqueda}")
    public String buscarPersonasUrl(@PathVariable(required = false) String busqueda, Model model) throws IOException{

        logger.info(busqueda);

        if (busqueda.length() >= 3){
            List<PersonaElastic> personasEncontradas = new ArrayList<>();
            if (busqueda != null) {
                personasEncontradas = getPersonas(busqueda);
            }

            List<PersonaDTO> personasEncontradasDTO = new ArrayList<>();
            for (int i=0; i<personasEncontradas.size(); i++){
                PersonaDTO personaDTO = personasEncontradas.get(i).toDTO();
                personasEncontradasDTO.add(personaDTO);
            }

            model.addAttribute("personasEncontradas", personasEncontradasDTO);

            logger.info(busqueda);
            logger.info(personasEncontradasDTO.toString());
        }else{
            logger.info("La palabra es demasiado corta");
        }

        return "search-results";
    }

    /*
    @RequestMapping(value = "/searchPersonalDepartamento/{busqueda}")
    public String buscarPersonasDepartamentoUrl(@PathVariable(required = false) String busqueda, Model model) throws IOException{

        logger.info(busqueda);

        if (busqueda.length() >= 3){
            List<PersonaElastic> personasEncontradas = new ArrayList<>();
            if (busqueda != null) {
                personasEncontradas = getPersonasByDepartamento(busqueda);
            }

            List<PersonaDTO> personasEncontradasDTO = new ArrayList<>();
            for (int i=0; i<personasEncontradas.size(); i++){
                PersonaDTO personaDTO = personasEncontradas.get(i).toDTO();
                personasEncontradasDTO.add(personaDTO);
            }

            model.addAttribute("personasEncontradas", personasEncontradasDTO);

            logger.info(busqueda);
            logger.info(personasEncontradasDTO.toString());
        }else{
            logger.info("La palabra es demasiado corta");
        }

        return "search-results";
    }
*/

    @GetMapping("/searchNombres")
    public ResponseEntity<List<PersonaDTO>> buscarNombres(@RequestParam(name = "nombre", required = false) String busqueda) throws IOException {

        List<PersonaElastic> personasEncontradas = new ArrayList<>();
        if (busqueda != null) {
            personasEncontradas = getPersonas(busqueda);
        }

        List<PersonaDTO> personasEncontradasDTO = new ArrayList<>();
        for (int i=0; i<personasEncontradas.size(); i++){
            PersonaDTO personaDTO = personasEncontradas.get(i).toDTO();
            personasEncontradasDTO.add(personaDTO);
        }

        return ResponseEntity.ok(personasEncontradasDTO);
    }

    @GetMapping("/searchPublicaciones")
    public ResponseEntity<List<PersonaElastic>> buscarPublicaciones(@RequestParam("publicacion") String busqueda) {
        //todo
        List<PersonaElastic> personasEncontradas = new ArrayList<>();
        return ResponseEntity.ok(personasEncontradas);
    }

    public String normalize(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+","");
    }

    public List<PersonaElastic> getPersonas(@NotNull String busqueda) throws IOException {

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
            String nombre_completo = normalize(personas.get(i).getNombre_completo()).toLowerCase();

            if (nombre_completo.contains(busqueda)){
                personasEncontradas.add(personas.get(i));
            }
        }
        return personasEncontradas;
    }

    public List<PersonaElastic> getPersonasByDepartamento(@NotNull String busqueda) throws IOException{

        String result = String.valueOf(elasticsearchRepository.searchByDepartament(busqueda));

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
            String nombre_completo = normalize(personas.get(i).getNombre_completo()).toLowerCase();

            if (nombre_completo.contains(busqueda)){
                personasEncontradas.add(personas.get(i));
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
}