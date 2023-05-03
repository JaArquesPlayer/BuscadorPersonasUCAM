package com.example.buscadorpersonasucam.services;

import com.example.buscadorpersonasucam.beans.DTO.PersonaDTO;
import com.example.buscadorpersonasucam.beans.DTO.ResultadoBusquedaDTO;
import com.example.buscadorpersonasucam.database.entity.PersonaElastic;
import com.example.buscadorpersonasucam.repository.ElasticsearchRepository;
import org.apache.commons.text.WordUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonaService {

    private final static Logger logger = Logger.getLogger("com.example.buscadorpersonasucam.controller.IndexController");
    private ElasticsearchRepository elasticsearchRepository;

    public PersonaService(ElasticsearchRepository elasticsearchRepository) {
        this.elasticsearchRepository = elasticsearchRepository;
    }

    public List<PersonaDTO> getAllPersonasByNombre(@NotNull String busqueda) throws IOException {

        busqueda = normalize(busqueda);
        SearchPage<PersonaElastic> result = elasticsearchRepository.searchAllByNombre(busqueda, false, false);

        List<PersonaDTO> personas = new ArrayList<>();
        for (SearchHit<PersonaElastic> searchHit : result.getSearchHits()) {
            PersonaElastic personaElastic = searchHit.getContent();
            PersonaDTO personaDTO = personaElastic.toDTO();
            personas.add(personaDTO);
        }

        return personas;
    }

    public ResultadoBusquedaDTO getPersonas(String texto, Pageable p) {
        SearchPage<PersonaElastic> personaElastic;
        personaElastic = elasticsearchRepository.busquedaResultados(texto, p, false, true);

        List<PersonaDTO> listaResultados = new ArrayList<>();
        ResultadoBusquedaDTO resultadoBusquedaDTO = new ResultadoBusquedaDTO();
        resultadoBusquedaDTO.setResultadosProtegidos(false);

        if (p.getPageNumber() == 0) {
            if (elasticsearchRepository.busquedaResultados(texto, p, true, true).getTotalElements() != personaElastic.getTotalElements()) {
                resultadoBusquedaDTO.setResultadosProtegidos(true);
            }
        }

        for (SearchHit<PersonaElastic> it : personaElastic.getSearchHits()) {
            PersonaDTO personaDTO = new PersonaDTO();
            personaDTO.setNombre(WordUtils.capitalizeFully(it.getContent().getNombre_completo()));
            if (it.getContent().getCorreos_institucionales() != null) {
                personaDTO.setEmail(it.getContent().getCorreos_institucionales().stream().filter(c -> c.contains("@ucam.edu")).findFirst().orElse(null));
            }
            if (it.getContent().getUbicacion() != null) {
                personaDTO.setUbicacion(WordUtils.capitalizeFully(it.getContent().getUbicacion()));
            }
            personaDTO.setId(it.getContent().getId_ucam());
            personaDTO.setExtension(it.getContent().getExtension());
            personaDTO.setSexo(it.getContent().getSexo());
            personaDTO.setFoto(Base64.getEncoder().encodeToString(it.getContent().getFoto()));
            personaDTO.setAlias_web(it.getContent().getAlias_web());
            personaDTO.setTelefono_ucam(it.getContent().getTelefono_ucam());

            if (it.getContent().getLinkedin() != null && it.getContent().getLinkedin().length() != 0) {
                if (it.getContent().getLinkedin().contains("linkedin.com") && it.getContent().getLinkedin().startsWith("https")) {
                    personaDTO.setLinkedin(it.getContent().getLinkedin());
                }
                if (it.getContent().getLinkedin().contains("linkedin.com") && it.getContent().getLinkedin().startsWith("www")) {
                    personaDTO.setLinkedin("https://" + it.getContent().getLinkedin());
                }
                if (!it.getContent().getLinkedin().contains("linkedin.com")) {
                    personaDTO.setLinkedin("https://www.linkedin.com/in/" + it.getContent().getLinkedin());
                }
            }
            if (it.getContent().getTwitter() != null && it.getContent().getTwitter().length() != 0) {
                if (it.getContent().getTwitter().contains("twitter.com") && it.getContent().getTwitter().startsWith("https")) {
                    personaDTO.setTwitter(it.getContent().getTwitter());
                }
                if (it.getContent().getTwitter().contains("twitter.com") && it.getContent().getTwitter().startsWith("www")) {
                    personaDTO.setTwitter("https://" + it.getContent().getTwitter());
                }
                if (!it.getContent().getTwitter().contains("twitter.com")) {
                    personaDTO.setTwitter("https://www.twitter.com/" + it.getContent().getTwitter());
                }
            }
            if (it.getContent().getFacebook() != null && it.getContent().getFacebook().length() != 0) {
                if (it.getContent().getFacebook().contains("facebook.com") && it.getContent().getFacebook().startsWith("https")) {
                    personaDTO.setFacebook(it.getContent().getFacebook());
                }
                if (it.getContent().getFacebook().contains("facebook.com") && it.getContent().getFacebook().startsWith("www")) {
                    personaDTO.setFacebook("https://" + it.getContent().getFacebook());
                }
                if (!it.getContent().getFacebook().contains("facebook.com")) {
                    personaDTO.setFacebook("https://www.facebook.com/" + it.getContent().getFacebook());
                }
            }
            if (it.getContent().getYoutube() != null && it.getContent().getYoutube().length() != 0) {
                if (it.getContent().getYoutube().contains("youtube.com") && it.getContent().getYoutube().startsWith("https")) {
                    personaDTO.setYoutube(it.getContent().getYoutube());
                }
                if (it.getContent().getYoutube().contains("youtube.com") && it.getContent().getYoutube().startsWith("www")) {
                    personaDTO.setYoutube("https://" + it.getContent().getYoutube());
                }
                if (!it.getContent().getYoutube().contains("youtube.com")) {
                    personaDTO.setYoutube("https://www.youtube.com/user/" + it.getContent().getYoutube());
                }
            }
            if (it.getContent().getInstagram() != null && it.getContent().getInstagram().length() != 0) {
                if (it.getContent().getInstagram().contains("instagram.com") && it.getContent().getInstagram().startsWith("https")) {
                    personaDTO.setInstagram(it.getContent().getInstagram());
                }
                if (it.getContent().getInstagram().contains("instagram.com") && it.getContent().getInstagram().startsWith("www")) {
                    personaDTO.setInstagram("https://" + it.getContent().getInstagram());
                }
                if (!it.getContent().getInstagram().contains("instagram.com")) {
                    personaDTO.setInstagram("https://www.instagram.com/" + it.getContent().getInstagram());
                }
            }
            listaResultados.add(personaDTO);
        }

        resultadoBusquedaDTO.setPageNumber(p.getPageNumber());
        resultadoBusquedaDTO.setPageSize(p.getPageSize());
        resultadoBusquedaDTO.setListaPersonas(listaResultados);
        resultadoBusquedaDTO.setTotalItems(elasticsearchRepository.totalHits);
        resultadoBusquedaDTO.setTotalPages((elasticsearchRepository.totalHits / p.getPageSize()));
        if (elasticsearchRepository.totalHits % p.getPageSize() > 0) {
            resultadoBusquedaDTO.setTotalPages(resultadoBusquedaDTO.getTotalPages() + 1);
        }
        logger.info("Retorno ResultadoBusquedaDTO");
        return resultadoBusquedaDTO;
    }

    public String normalize(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+","");
    }
}

