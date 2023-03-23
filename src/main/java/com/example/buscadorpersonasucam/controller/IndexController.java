package com.example.buscadorpersonasucam.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String perfil() {
        return "profile";
    }

    @GetMapping("/search")
    public ResponseEntity<List<Persona>> buscarPersonas(@RequestParam("nombre") String busqueda) {

        if(busqueda.length() < 3){
            List<Persona> emptyList = new ArrayList<>();
            return ResponseEntity.ok(emptyList);
        }

        //Datos de llegada, son ficticios pero deben de ser los reales.
        List<Persona> personas = new ArrayList<>();

        personas.add(new Persona(1, "Jose Ángel", "Alcolea Arques"));
        personas.add(new Persona(2, "Jose Manuel", "Martín Melero"));
        personas.add(new Persona(3, "Joselito", "Jiménez Fernández"));
        personas.add(new Persona(4, "Laura", "Martínez Ortega"));
        personas.add(new Persona(5, "María", "Caldel García"));
        personas.add(new Persona(6, "María del Carmen", "Astero Martín"));
        //Fin de datos de lledada

        List<Persona> personasEncontradas = new ArrayList<>();

        for(int i=0; i<personas.size(); i++){
            String nombreCompleto = personas.get(i).getNombre() + " " + personas.get(i).getApellidos();

            busqueda = normalize(busqueda).toLowerCase();
            nombreCompleto = normalize(nombreCompleto).toLowerCase();

            if(nombreCompleto.contains(busqueda)){
                personasEncontradas.add(personas.get(i));
            }
        }
        return ResponseEntity.ok(personasEncontradas);
    }

    public String normalize(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+","");
    }
}