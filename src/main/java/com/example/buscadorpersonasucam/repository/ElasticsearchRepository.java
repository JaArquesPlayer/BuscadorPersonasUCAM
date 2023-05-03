package com.example.buscadorpersonasucam.repository;

import com.example.buscadorpersonasucam.ElasticsearchConfig;
import com.example.buscadorpersonasucam.database.entity.PersonaElastic;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.functionscore.FieldValueFactorFunctionBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;


import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@Repository
public class ElasticsearchRepository {

    private final static Logger logger = Logger.getLogger("com.example.buscadorpersonasucam.controller.IndexController");

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    public static Integer totalHits;

    private final RestHighLevelClient client;
    private ClientConfiguration clientConfiguration;

    public ElasticsearchRepository(RestHighLevelClient client) {
        this.client = client;
    }

    public SearchResponse searchAll() throws IOException {
        SearchRequest searchRequest = new SearchRequest("personas");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        return client.search(searchRequest, RequestOptions.DEFAULT);
    }

    public SearchResponse searchById(Integer id) throws IOException {
        SearchRequest searchRequest = new SearchRequest("personas");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("id", id));
        searchRequest.source(searchSourceBuilder);
        return client.search(searchRequest, RequestOptions.DEFAULT);
    }

    public SearchResponse searchAllByNombre(String busqueda) throws IOException{
        //todo
        RestHighLevelClient client = RestClients.create(clientConfiguration).rest();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder query = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("nombre_mostrar", busqueda));
        searchSourceBuilder.query(query);
        SearchRequest searchRequest = new SearchRequest("personas");
        searchRequest.source(searchSourceBuilder);

        return client.search(searchRequest, RequestOptions.DEFAULT);
    }

    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(1000L))
    public SearchPage<PersonaElastic> busquedaResultados(String texto, Pageable p, Boolean resultadosProtegidos, Boolean paginado) {

        //HashMap<String, List<String>> unidadesHijas = departamentoService.departamentosHijos(texto)

        final List<String> listaPalabras = new ArrayList<>();
        String[] palabras = texto.split(" ");
        for (String palabra : palabras) {
            listaPalabras.add(palabra.substring(0, 1) + "*" + palabra.substring(1) + "*");
        }
        String textoFinal = String.join(" AND ", listaPalabras).trim();
        Map<String, Float> mapaCampos = new HashMap<>() {
            {
                put("nombre", 1.0f);
                put("apellido1", 1.0f);
                put("apellido2", 1.0f);
                put("ubicacion", 1.0f);
                put("cargos.departamento", 1.0f);
                put("extension", 1.0f);
                put("correos_institucionales", 1.0f);
                put("areas_conocimiento", 1.0f);
            }
        };

        FunctionScoreQueryBuilder.FilterFunctionBuilder[] function_score = new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(new FieldValueFactorFunctionBuilder("visitas_buscador").factor(1000000).modifier(FieldValueFactorFunction.Modifier.SQUARE).missing(0)),
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("nombre", texto), ScoreFunctionBuilders.weightFactorFunction(100000)),
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("apellido1", texto), ScoreFunctionBuilders.weightFactorFunction(1000)),
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("apellido2", texto), ScoreFunctionBuilders.weightFactorFunction(100)),
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("ubicacion", texto), ScoreFunctionBuilders.weightFactorFunction(50)),
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("correos_institucionales", texto), ScoreFunctionBuilders.weightFactorFunction(1))
        };

        QueryBuilder queryBuilder = QueryBuilders.boolQuery();

        ((BoolQueryBuilder) queryBuilder).must(QueryBuilders.functionScoreQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.queryStringQuery(textoFinal).fields(mapaCampos)), function_score)
                        .scoreMode(FunctionScoreQuery.ScoreMode.MAX).boostMode(CombineFunction.MULTIPLY)
        );

        /*if (usuarioAutenticado || resultadosProtegidos) {
            ((BoolQueryBuilder) queryBuilder).mustNot(QueryBuilders.termQuery("privacidad.keyword", "PRIVADO"));
        } else {
            ((BoolQueryBuilder) queryBuilder).mustNot(QueryBuilders.termsQuery("privacidad.keyword", new ArrayList<String>(Arrays.asList("PROTEGIDO", "PRIVADO"))));
        }*/

        ((BoolQueryBuilder) queryBuilder).mustNot(QueryBuilders.termsQuery("privacidad.keyword", new ArrayList<String>(Arrays.asList("PROTEGIDO", "PRIVADO"))));
        ((BoolQueryBuilder) queryBuilder).filter(QueryBuilders.termsQuery("colectivo", "pas", "pdi"));

        Query query;

        if (paginado) {
            query = new NativeSearchQueryBuilder()
                    .withQuery(queryBuilder)
                    .withFields("id_ucam", "nombre_completo", "correos_institucionales", "foto", "departamentos", "extension",
                            "alias_web", "ubicacion", "colectivo", "facebook", "instagram", "twitter", "linkedin",
                            "youtube", "web", "cargos.departamento", "telefono_ucam")
                    .withPageable(p)
                    .withTrackScores(true)
                    .build();
        } else {
                    query = new NativeSearchQueryBuilder()
                    .withQuery(queryBuilder)
                    .withFields("id_ucam", "nombre_completo", "correos_institucionales", "foto", "departamentos", "extension",
                            "alias_web", "ubicacion", "colectivo", "facebook", "instagram", "twitter", "linkedin",
                            "youtube", "web", "cargos.departamento", "telefono_ucam")
                    .withTrackScores(true)
                    .build();
        }

        SearchHits<PersonaElastic> list = elasticsearchRestTemplate.search(query, PersonaElastic.class);
        SearchPage<PersonaElastic> listPaginated = SearchHitSupport.searchPageFor(list, query.getPageable());

        totalHits = Math.toIntExact(list.getTotalHits());
        logger.info("Retorno resultados personas");

        return listPaginated;
    }
}
