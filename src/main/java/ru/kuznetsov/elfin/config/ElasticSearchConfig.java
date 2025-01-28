package ru.kuznetsov.elfin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "ru.kuznetsov.elfin.repositories")
@ComponentScan(basePackages = {"ru.kuznetsov.elfin.models.entity"})
@RequiredArgsConstructor
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    private final CamundaConnection camundaConnection;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(camundaConnection.getElasticSearchHost() + ":" + camundaConnection.getElasticSearchPort())
                .build();
    }
}
