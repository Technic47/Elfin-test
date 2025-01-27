package ru.kuznetsov.elfin.services.contract;

import ru.kuznetsov.elfin.models.entity.ClientWithResultEntity;

public interface ClientWithResultService {
    void save(ClientWithResultEntity entity);

    Iterable<ClientWithResultEntity> findAll();
}
