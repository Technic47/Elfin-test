package ru.kuznetsov.elfin.services;

import io.camunda.zeebe.client.api.response.Decision;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.EvaluateDecisionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kuznetsov.elfin.connectors.contract.CamundaConnector;
import ru.kuznetsov.elfin.models.dto.ClientDto;
import ru.kuznetsov.elfin.services.contract.ClientService;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    @Value("${bpmn.location}")
    private String BPMN_LOCATION;
    @Value("${dmn.location}")
    private String DMN_LOCATION;
    public static final String INN_VARIABLE = "inn";
    public static final String REGION_VARIABLE = "region";
    public static final String CAPITAL_VARIABLE = "capital";
    public static final String PASS_VARIABLE = "pass";
    private final CamundaConnector camundaConnector;

    @Override
    public Boolean gradeClient(ClientDto clientInfo) {
        DeploymentEvent deploymentEvent = camundaConnector.deployProcess(DMN_LOCATION);

        String dmnDecisionId = deploymentEvent.getDecisions()
                .stream()
                .max(Comparator.comparingInt(Decision::getVersion))
                .get()
                .getDmnDecisionId();

        EvaluateDecisionResponse decisionResult = camundaConnector.evaluateDecision(dmnDecisionId, getVariables(clientInfo));
        return !decisionResult.getDecisionOutput().equals("false");
    }

    private Map<String, Object> getVariables(ClientDto clientInfo) {
        Map<String, Object> variables = new HashMap<>();
        variables.put(INN_VARIABLE, clientInfo.getInn());
        variables.put(REGION_VARIABLE, clientInfo.getRegion());
        variables.put(CAPITAL_VARIABLE, clientInfo.getCapital());

        return variables;
    }
}
