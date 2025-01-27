package ru.kuznetsov.elfin.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuznetsov.elfin.models.entity.ClientWithResultEntity;
import ru.kuznetsov.elfin.repositories.ClientWithResultRepository;
import ru.kuznetsov.elfin.services.contract.ClientWithResultService;

@Service
@RequiredArgsConstructor
public class ClientWithResultServiceImpl implements ClientWithResultService {

    private final ClientWithResultRepository repo;

    @Override
    public void save(ClientWithResultEntity entity) {
        repo.save(entity);
    }

    @Override
    public Iterable<ClientWithResultEntity> findAll() {
        return repo.findAll();
    }
}
