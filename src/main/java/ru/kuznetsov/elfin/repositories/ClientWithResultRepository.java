package ru.kuznetsov.elfin.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ru.kuznetsov.elfin.models.entity.ClientWithResultEntity;

import java.util.Optional;

public interface ClientWithResultRepository extends ElasticsearchRepository<ClientWithResultEntity, String> {
    Optional<ClientWithResultEntity> findByInn(String clientInn);
}
