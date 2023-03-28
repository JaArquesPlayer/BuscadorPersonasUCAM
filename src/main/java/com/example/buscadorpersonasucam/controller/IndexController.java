package com.example.buscadorpersonasucam.controller;

import com.example.buscadorpersonasucam.database.entity.PersonaElastic;
import org.elasticsearch.search.SearchHits;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

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

        //todo Aqui hace el pedido al backend con la id que se le pasa {id}
        //devuelde una persona completa con todos los datos para usarlos en el frontend

        return "profile";
    }

    @GetMapping("/searchPersonal")
    public ResponseEntity<List<PersonaElastic>> buscarPersonas(@RequestParam("nombre") String busqueda) {

        List<PersonaElastic> personasEncontradas = new ArrayList<>();
        return ResponseEntity.ok(personasEncontradas);
    }

    @GetMapping("/searchPublicaciones")
    public ResponseEntity<List<PersonaElastic>> buscarPublicaciones(@RequestParam("publicacion") String busqueda) {

        List<PersonaElastic> personasEncontradas = new ArrayList<>();
        return ResponseEntity.ok(personasEncontradas);
    }

    public String normalize(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+","");
    }
}