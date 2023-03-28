package com.example.buscadorpersonasucam.controller;

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
        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona(1, "Jose Ángel", "Alcolea Arques"));
        personas.add(new Persona(2, "Jose Manuel", "Martín Melero"));
        personas.add(new Persona(3, "Joselito", "Jiménez Fernández"));
        personas.add(new Persona(4, "Laura", "Martínez Ortega"));
        personas.add(new Persona(5, "María", "Caldel García"));
        personas.add(new Persona(6, "María del Carmen", "Astero Martín"));

        for (int i=0; i<personas.size(); i++){
            if(personas.get(i).getId() == id){
                model.addAttribute("nombre", personas.get(i).getNombre());
                model.addAttribute("apellidos", personas.get(i).getApellidos());
            }
        }
        return "profile";
    }

    @GetMapping("/searchPersonal")
    public ResponseEntity<List<Persona>> buscarPersonas(@RequestParam("nombre") String busqueda) {

        if(busqueda.length() < 3){
            List<Persona> emptyList = new ArrayList<>();
            return ResponseEntity.ok(emptyList);
        }

        //todo Datos de llegada, son ficticios pero deben de ser los reales.
        List<Persona> personas = new ArrayList<>();

        personas.add(new Persona(1, "Jose Ángel", "Alcolea Arques"));
        personas.add(new Persona(2, "Jose Manuel", "Martín Melero"));
        personas.add(new Persona(3, "Joselito", "Jiménez Fernández"));
        personas.add(new Persona(4, "Laura", "Martínez Ortega"));
        personas.add(new Persona(5, "María", "Caldel García"));
        personas.add(new Persona(6, "María del Carmen", "Astero Martín"));

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

    @GetMapping("/searchPublicaciones")
    public ResponseEntity<List<Publicacion>> buscarPublicaciones(@RequestParam("publicacion") String busqueda) {

        if(busqueda.length() < 3){
            List<Publicacion> emptyList = new ArrayList<>();
            return ResponseEntity.ok(emptyList);
        }

        //todo Datos de llegada, son ficticios pero deben de ser los reales.
        List<Publicacion> publicaciones = new ArrayList<>();

        publicaciones.add(new Publicacion(1, "Organizaciones nativas responsables: la RSC en la cultura de las startups digitales españolas", "María Dolores Gil , Margarita Capdepón , Miguel Ángel Beltrán , Francisco José Noguera , Ginesa Martinez , María Dolores García , Jorge López , María Fernández , Victoria Pérez , Mario Cava (2015)"));
        publicaciones.add(new Publicacion(2, "Propuesta de diseño claro y transparente para la factura eléctrica regulada en España", "Blas José Subiela , María Ascensión Miralles , David Sánchez (2018)"));
        publicaciones.add(new Publicacion(3, "Influencia del diseño periodístico en la comprensión de la información", "Blas José Subiela , Ariana Gómez (2018)"));

        List<Publicacion> publicacionesEncontradas = new ArrayList<>();

        for (int i=0; i<publicaciones.size(); i++){
            busqueda = normalize(busqueda).toLowerCase();
            String titulo = normalize(publicaciones.get(i).getTitulo());
            String contenido = normalize(publicaciones.get(i).getContenido());

            if(titulo.contains(busqueda) || contenido.contains(busqueda)){
                publicacionesEncontradas.add(publicaciones.get(i));
            }
        }

        return ResponseEntity.ok(publicacionesEncontradas);
    }


    public String normalize(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+","");
    }
}