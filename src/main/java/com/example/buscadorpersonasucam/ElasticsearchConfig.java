package com.example.buscadorpersonasucam;

        import org.apache.http.HttpHost;
        import org.apache.http.auth.AuthScope;
        import org.apache.http.auth.UsernamePasswordCredentials;
        import org.apache.http.client.CredentialsProvider;
        import org.apache.http.client.config.RequestConfig;
        import org.apache.http.impl.client.BasicCredentialsProvider;
        import org.elasticsearch.client.RestClient;
        import org.elasticsearch.client.RestClientBuilder;
        import org.elasticsearch.client.RestHighLevelClient;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.data.elasticsearch.client.ClientConfiguration;
        import org.springframework.data.elasticsearch.client.RestClients;
        import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
        import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

        import java.time.Duration;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.buscadorpersonasucam.repository")
public class ElasticsearchConfig {

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

    @Bean(destroyMethod = "close")
    public static RestHighLevelClient restHighLevelClient() {

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("", ""));

        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                .setDefaultCredentialsProvider(credentialsProvider)
                .setDefaultRequestConfig(RequestConfig.custom()
                .setConnectTimeout(5000)
                .setSocketTimeout(5000)
                .setConnectionRequestTimeout(0)
                .build()));

        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }
}