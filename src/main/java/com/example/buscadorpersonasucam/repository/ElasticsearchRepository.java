package com.example.buscadorpersonasucam.repository;

import co.elastic.clients.elasticsearch._types.aggregations.AggregationBuilders;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class ElasticsearchRepository {

    private final RestHighLevelClient client;

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

    public SearchResponse searchAllAndReturnFields() throws IOException {
        SearchRequest searchRequest = new SearchRequest("personas");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.fetchSource(new String[] {"nombre_completo", "nombre_mostrar", "id", "correor_personales", "correos_institucionales", "telefonos", "extension", "instagram", "linkedin", "twitter", "ubicacion", "foto"}, null);
        searchRequest.source(searchSourceBuilder);
        return client.search(searchRequest, RequestOptions.DEFAULT);
    }

    public SearchResponse searchById(Integer id) throws IOException {
        SearchRequest searchRequest = new SearchRequest("personas");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("id", id));
        searchSourceBuilder.fetchSource(new String[]{"nombre_completo", "nombre_mostrar", "id", "correor_personales", "correos_institucionales", "telefonos", "extension", "instagram", "linkedin", "twitter", "ubicacion", "foto"}, null);
        searchRequest.source(searchSourceBuilder);
        return client.search(searchRequest, RequestOptions.DEFAULT);
    }

}
