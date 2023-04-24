package com.example.buscadorpersonasucam;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.List;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.buscadorpersonasucam.repository")
public class ElasticsearchConfig {

    @Value("${elasticsearch.host}")
    private String elasticsearchHost;

    @Value("${elasticsearch.port}")
    private int elasticsearchPort;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient eslasticsearchClient(){
        return new RestHighLevelClient(RestClient.builder(new HttpHost(elasticsearchHost, elasticsearchPort, "http")));
    }
}