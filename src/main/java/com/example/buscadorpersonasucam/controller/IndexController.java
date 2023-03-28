package com.example.buscadorpersonasucam.controller;

import com.example.buscadorpersonasucam.beans.DTO.PersonaDTO;
import com.example.buscadorpersonasucam.database.entity.PersonaElastic;
import com.example.buscadorpersonasucam.repository.ElasticsearchRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
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
import java.util.List;

@Controller
public class IndexController {

    private final ElasticsearchRepository elasticsearchRepository;

    public IndexController(ElasticsearchRepository elasticsearchRepository) {
        this.elasticsearchRepository = elasticsearchRepository;
    }

    @RequestMapping(value = "/personas")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/resultados")
    public String resultados() {
        return "search-results";
    }

    @RequestMapping(value = "/perfil/{id}")
    public String perfil(Model model, @PathVariable int id) {
        //todo utilizar la busqueda por id para devolver una persona completa o lo necesario

        //PersonaElastic persona = elasticsearchRepository.;
        //model.addAttribute("nombre",);

        return "profile";
    }

    @GetMapping("/searchPersonal")
    public ResponseEntity<List<PersonaDTO>> buscarPersonas(@RequestParam("nombre") String busqueda) throws IOException {

        if (busqueda.length() < 3){
            List<PersonaDTO> emptyList = new ArrayList<>();
            return ResponseEntity.ok(emptyList);
        }

        String result = String.valueOf(elasticsearchRepository.searchAllAndReturnFields());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(result);
        JsonNode hitsNode = rootNode.get("hits").get("hits");
        List<PersonaDTO> personas = new ArrayList<>();

        for (JsonNode hit : hitsNode) {
            JsonNode sourceNode = hit.get("_source");
            PersonaDTO personaDTO = mapper.treeToValue(sourceNode, PersonaDTO.class);
            personas.add(personaDTO);
        }

        List<PersonaDTO> personasEncontradas = new ArrayList<>();

        for (int i=0; i< personas.size(); i++){
            busqueda = normalize(busqueda).toLowerCase();
            String nombre_completo = normalize(personas.get(i).getNombre_completo()).toLowerCase();

            if (nombre_completo.contains(busqueda)){
                personasEncontradas.add(personas.get(i));
            }
        }
        return ResponseEntity.ok(personasEncontradas);
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
}