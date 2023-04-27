package com.example.buscadorpersonasucam.controller;

import com.example.buscadorpersonasucam.beans.DTO.PersonaDTO;
import com.example.buscadorpersonasucam.database.entity.PersonaElastic;
import com.example.buscadorpersonasucam.repository.ElasticsearchRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.Base64;
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

    @RequestMapping(value = "/image-response-entity/{id}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImageAsResponseEntity(@PathVariable int id) throws IOException {
        HttpHeaders headers = new HttpHeaders();

        PersonaDTO persona = getPersonaById(id).toDTO();
        String foto = persona.getFoto();
        byte[] bytes = Base64.getDecoder().decode(foto);

        //prueba
        //File fichero = new File("C:/11.jpg");
        //InputStream in = new FileInputStream(fichero);
        //byte[] media = IOUtils.toByteArray(in);
        //prueba

        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, HttpStatus.OK);
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
