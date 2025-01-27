package ru.kuznetsov.elfin.controllers.contract;

import org.springframework.http.ResponseEntity;
import ru.kuznetsov.elfin.models.dto.ClientDto;

public interface ClientGradeController {
    ResponseEntity<Boolean> gradeClient(ClientDto clientInfo);
}
