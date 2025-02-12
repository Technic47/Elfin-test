package ru.kuznetsov.elfin.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kuznetsov.elfin.models.dto.ClientDto;
import ru.kuznetsov.elfin.models.entity.ClientWithResultEntity;
import ru.kuznetsov.elfin.services.contract.ClientService;
import ru.kuznetsov.elfin.services.contract.ClientWithResultService;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
@Tag(name = "client-api", description = "API 'Клиенты'")
public class ClientGradeControllerImpl {

    private final ClientService clientService;
    private final ClientWithResultService clientWithResultService;
    private static final Logger LOG = LogManager.getLogger(ClientGradeControllerImpl.class);


    @PostMapping("/grade")
    @Operation(summary = "Оценка клиента", description = "Обновление данных устройства")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ClientDto.class)
                    ),
                    description = "Данные о клиенте"
            )
    })
    public ResponseEntity<Boolean> gradeClient(@Valid @RequestBody ClientDto clientInfo) {
        LOG.info("Client info: {}", clientInfo);
        Boolean result = clientService.gradeClient(clientInfo);
        clientWithResultService.save(new ClientWithResultEntity(clientInfo, result));
        return ResponseEntity.ok(result);
    }
}
