package ru.kuznetsov.elfin.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionResult;
import org.camunda.bpm.dmn.engine.DmnDecisionResultEntries;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kuznetsov.elfin.models.dto.ClientDto;
import ru.kuznetsov.elfin.services.contract.ClientService;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    @Value("${dmn.location}")
    private String DMN_LOCATION;
    public static final String INN_VARIABLE = "inn";
    public static final String REGION_VARIABLE = "region";
    public static final String CAPITAL_VARIABLE = "capital";
    public static final String RESULT_VARIABLE = "result";
    private final DmnEngine dmnEngine;

    @Override
    public Boolean gradeClient(ClientDto clientInfo) {
        DmnModelInstance modelInstance = Dmn.readModelFromFile(new File(DMN_LOCATION));

        List<DmnDecision> dmnDecisions = dmnEngine.parseDecisions(modelInstance);
        DmnDecision dmnDecision = dmnDecisions.get(0);

        DmnDecisionResult dmnDecisionResultEntries = dmnEngine.evaluateDecision(dmnDecision, getVariables(clientInfo));
        DmnDecisionResultEntries firstResult = dmnDecisionResultEntries.getFirstResult();

        return firstResult == null || !firstResult.get(RESULT_VARIABLE).equals(false);
    }

    private Map<String, Object> getVariables(ClientDto clientInfo) {
        Map<String, Object> variables = new HashMap<>();
        variables.put(INN_VARIABLE, clientInfo.getInn());
        variables.put(REGION_VARIABLE, clientInfo.getRegion());
        variables.put(CAPITAL_VARIABLE, clientInfo.getCapital());

        return variables;
    }
}
