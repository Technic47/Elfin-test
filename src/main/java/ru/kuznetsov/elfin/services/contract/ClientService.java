package ru.kuznetsov.elfin.services.contract;

import ru.kuznetsov.elfin.models.dto.ClientDto;

public interface ClientService {
    Boolean gradeClient(ClientDto clientInfo);
}
