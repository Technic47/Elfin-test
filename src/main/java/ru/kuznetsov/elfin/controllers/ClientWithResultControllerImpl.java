package ru.kuznetsov.elfin.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kuznetsov.elfin.controllers.contract.ClientWithResultController;
import ru.kuznetsov.elfin.models.entity.ClientWithResultEntity;
import ru.kuznetsov.elfin.services.contract.ClientWithResultService;

@RestController
@RequestMapping("/api/v1/client/results")
@RequiredArgsConstructor
public class ClientWithResultControllerImpl implements ClientWithResultController {

    private final ClientWithResultService clientWithResultService;

    @Override
    @GetMapping("/all")
    public ResponseEntity<Iterable<ClientWithResultEntity>> getAllClientsWithResults() {
        return ResponseEntity.ok(clientWithResultService.findAll());
    }
}
