package ru.kuznetsov.elfin.services;

import ru.kuznetsov.elfin.models.dto.ClientDto;

public interface ClientService {
    Boolean gradeClient(ClientDto clientInfo);
}
