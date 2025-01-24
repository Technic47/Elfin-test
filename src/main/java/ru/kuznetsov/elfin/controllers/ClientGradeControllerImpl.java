package ru.kuznetsov.elfin.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kuznetsov.elfin.models.dto.ClientDto;
import ru.kuznetsov.elfin.services.ClientService;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientGradeControllerImpl implements ClientGradeController {

    private final ClientService clientService;

    @Override
    @PostMapping("/grade")
    public ResponseEntity<Boolean> gradeClient(ClientDto clientInfo) {
        return ResponseEntity.ok(clientService.gradeClient(clientInfo));
    }
}
