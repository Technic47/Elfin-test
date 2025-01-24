package ru.kuznetsov.elfin.services;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.Process;
import io.camunda.zeebe.client.api.response.ProcessInstanceResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kuznetsov.elfin.models.dto.ClientDto;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private static final String BPMN_LOCATION = "src/main/resources/bpmn/ElfinTest.bpmn";
    public static final String INN_VARIABLE = "inn";
    public static final String REGION_VARIABLE = "region";
    public static final String CAPITAL_VARIABLE = "capital";
    public static final String PASS_VARIABLE = "pass";
    private final ZeebeClient zeebeClient;

    @Override
    public Boolean gradeClient(ClientDto clientInfo) {
        DeploymentEvent deploymentEvent = deployClientGradeProcess();

        String bpmnProcessId = deploymentEvent.getProcesses()
                .stream()
                .max(Comparator.comparingInt(Process::getVersion))
                .get()
                .getBpmnProcessId();

        ProcessInstanceResult result = createClientGradeInstance(bpmnProcessId, clientInfo);
        return (Boolean) result.getVariablesAsMap().get(PASS_VARIABLE);
    }

    private DeploymentEvent deployClientGradeProcess() {
        return zeebeClient.newDeployResourceCommand()
                .addResourceFile(BPMN_LOCATION)
                .send()
                .join();

    }

    private ProcessInstanceResult createClientGradeInstance(String bpmnProcessId, ClientDto clientInfo) {
        return zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(bpmnProcessId)
                .latestVersion()
                .variables(getVariables(clientInfo))
                .withResult()
                .send()
                .join();
    }

    private Map<String, Object> getVariables(ClientDto clientInfo) {
        Map<String, Object> variables = new HashMap<>();
        variables.put(INN_VARIABLE, clientInfo.getInn());
        variables.put(REGION_VARIABLE, clientInfo.getRegion());
        variables.put(CAPITAL_VARIABLE, clientInfo.getCapital());

        return variables;
    }
}
