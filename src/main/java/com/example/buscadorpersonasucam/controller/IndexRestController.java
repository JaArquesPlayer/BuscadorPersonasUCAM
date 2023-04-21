package com.example.buscadorpersonasucam.controller;

import com.example.buscadorpersonasucam.beans.DTO.PersonaDTO;
import com.example.buscadorpersonasucam.database.entity.PersonaElastic;
import com.example.buscadorpersonasucam.repository.ElasticsearchRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletContext;
import org.apache.commons.io.IOUtils;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

@RestController
public class IndexRestController {
    private final static Logger logger = Logger.getLogger("com.example.buscadorpersonasucam.controller.IndexController");

    private final ElasticsearchRepository elasticsearchRepository;

    private final ServletContext servletContext;

    public IndexRestController(ElasticsearchRepository elasticsearchRepository, ServletContext servletContext) {
        this.elasticsearchRepository = elasticsearchRepository;
        this.servletContext = servletContext;
    }

    @GetMapping(value = "/foto/{id}")
    public String foto(@PathVariable int id) throws IOException {

        PersonaDTO persona = getPersonaById(id).toDTO();
        String foto = "data:image/jpeg;base64," + persona.getFoto();

        return foto;
    }

    @RequestMapping(value = "/image-response-entity", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImageAsResponseEntity() throws IOException {
        HttpHeaders headers = new HttpHeaders();

        File fichero = new File("C:/11.jpg");
        InputStream in = servletContext.getResourceAsStream("");
        byte[] media = IOUtils.toByteArray(in);

        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
        return responseEntity;
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
