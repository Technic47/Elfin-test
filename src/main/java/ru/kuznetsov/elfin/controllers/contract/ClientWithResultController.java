package ru.kuznetsov.elfin.controllers.contract;

import org.springframework.http.ResponseEntity;
import ru.kuznetsov.elfin.models.entity.ClientWithResultEntity;

public interface ClientWithResultController {
    ResponseEntity<Iterable<ClientWithResultEntity>> getAllClientsWithResults();
}
