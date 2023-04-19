package com.example.buscadorpersonasucam.controller;

import com.example.buscadorpersonasucam.beans.DTO.PersonaDTO;
import com.example.buscadorpersonasucam.beans.DTO.PublicacionDTO;
import com.example.buscadorpersonasucam.database.entity.PersonaElastic;
import com.example.buscadorpersonasucam.repository.ElasticsearchRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
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
        PersonaDTO personaEncontradaDTO = new PersonaDTO();

        if (personaEncontrada != null) {
            personaEncontradaDTO = personaEncontrada.toDTO();
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

        String cargos = " | ";
        for (int i=0; i<personaEncontradaDTO.getCargos().size(); i++){
            cargos += personaEncontradaDTO.getCargos().get(i).getNombre();

            if(i != (personaEncontradaDTO.getCargos().size() - 1)){
                cargos += " - ";
            }
        }
        model.addAttribute("cargos", cargos);

        String cargos2 = " | ";
        for (int i=0; i<personaEncontradaDTO.getCargos().size(); i++){
            cargos2 += personaEncontradaDTO.getCargos().get(i).getNombre();

            if(i != (personaEncontradaDTO.getCargos().size() - 1)){
                cargos2 += "\n";
            }
        }
        model.addAttribute("cargos2", cargos2);

        //Todo la imagen del meta debe ser una url absoluta
        String imageUrl = "data:image/jpeg;base64,"+ personaEncontradaDTO.getFoto();
        model.addAttribute("fotoSrc", imageUrl);

        List<PublicacionDTO> publicacionesEncontradasDTO = new ArrayList<>();
        model.addAttribute("publicaciones", publicacionesEncontradasDTO);

        //todo debe devolverse los datos correctos
        model.addAttribute("proyectosCompetitivos", personasEncontradasDepartamentoDTO);
        model.addAttribute("proyectosNoCompetitivos", personasEncontradasDepartamentoDTO);
        model.addAttribute("docencia", personasEncontradasDepartamentoDTO);

        return "perfil";
    }

    @RequestMapping(value = "/searchPersonal/")
    public String buscarPersonas( @RequestParam(name = "search") String busqueda, Model model) {
        model.addAttribute("nombre", busqueda);

        return "resultados";
    }

    @RequestMapping(value = "/searchPersonal/{busqueda}")
    public String buscarPersonasUrl(@PathVariable String busqueda, Model model) throws IOException{

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
        }
        return "plantillas/personal";
    }

    @RequestMapping(value = "/searchDepartamentos/{busqueda}")
    public String buscarDepartamentosUrl(@PathVariable String busqueda, Model model) throws IOException{
        //todo devolver personas por departamento model.addAttribute("persoansEncontradas", personasEncontradasDTO);

        return "plantillas/departamentos";
    }

    @RequestMapping(value = "/searchPublicaciones/{busqueda}")
    public String buscarPublicaciones(@PathVariable String busqueda, Model model) throws IOException{
        //todo devolver publicaciones model.addAttribute("publicaciones", publicacionesEncontradasDTO);
        //recorrer todas las personas y comparar todas las publicaciones con lo introducido

        return "plantillas/publicaciones";
    }

    @RequestMapping(value = "/searchNombres/{busqueda}")
    public String buscarNombresUrl(@PathVariable(required = false) String busqueda, Model model) throws IOException{

        if (busqueda.length() >= 3){
            List<PersonaElastic> personasEncontradas = new ArrayList<>();
            if (busqueda != null) {
                personasEncontradas = getPersonas(busqueda);
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
            String ubicacion = normalize(personas.get(i).getUbicacion()).toLowerCase();

            if (ubicacion.equalsIgnoreCase(busqueda)){
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