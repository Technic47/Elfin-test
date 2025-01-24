package ru.kuznetsov.elfin.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kuznetsov.elfin.models.dto.ClientDto;

public interface ClientGradeController {
    ResponseEntity<Boolean> gradeClient(ClientDto clientInfo);
}
