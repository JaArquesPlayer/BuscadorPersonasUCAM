package com.example.buscadorpersonasucam;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.convert.MappingElasticsearchConverter;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.time.Duration;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.buscadorpersonasucam.repository")
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    @Value("${elasticsearch.host:null}")
    private String eHost;
    @Value("${elasticsearch.port:null}")
    private int ePort;
    @Value("${elasticsearch.clustername:null}")
    private String eClusterName;
    @Value("${elasticsearch.username:null}")
    private String eUsername;
    @Value("${elasticsearch.password:null}")
    private String ePassword;

    @Bean
    RestHighLevelClient client() {
        ClientConfiguration clientConfiguration  = null;
        if(eUsername != "null") {
            clientConfiguration  = ClientConfiguration.builder()
                    .connectedTo(eHost + ":" + ePort)
                    .withBasicAuth(eUsername, ePassword)
                    .withSocketTimeout(Duration.ofSeconds(10))
                    .withConnectTimeout(Duration.ofSeconds(10)).build();
        } else {
            clientConfiguration  = ClientConfiguration.builder()
                    .connectedTo(eHost + ":" + ePort)
                    .withSocketTimeout(Duration.ofSeconds(10))
                    .withConnectTimeout(Duration.ofSeconds(10)).build();
        }

        return RestClients.create(clientConfiguration).rest();
    }

    @Bean(name = "elasticsearchTemplate")
    ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(client());
    }

    @Override
    public RestHighLevelClient elasticsearchClient() {
        return client();
    }

    /*
    @Override
    public ElasticsearchConverter elasticsearchConverter() {
        return new MappingElasticsearchConverter(elasticsearchMappingContext());
    }
    */

    @Bean
    public ElasticsearchOperations elasticsearchOperations() {
        return new ElasticsearchRestTemplate(client());
    }
}