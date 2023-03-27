package com.example.buscadorpersonasucam.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String perfil(Model model) {

        //Aqui hace el pedido al backend con la id que se le pasa {id}
        //devuelde una persona completa con todos los datos para usarlos en el frontend

        Persona persona = new Persona();

        //model.addAttribute("nombre", persona.getNombre());
        //mandar de vuelta todos los valores para rellenar la pagina

        return "profile";
    }

    @GetMapping("/searchPersonal")
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
    @GetMapping("/searchDepartamentos")
    public ResponseEntity<List<Departamento>> buscarDepartamentos(@RequestParam("nombre") String busqueda) {

        List<Departamento> departamentosEncontrados = new ArrayList<>();

        return ResponseEntity.ok(departamentosEncontrados);
    }
    @GetMapping("/searchPublicaciones")
    public ResponseEntity<List<Publicacion>> buscarPublicaciones(@RequestParam("publicacion") String busqueda) {

        List<Publicacion> publicacionesEncontradas = new ArrayList<>();

        return ResponseEntity.ok(publicacionesEncontradas);
    }


    public String normalize(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+","");
    }
}