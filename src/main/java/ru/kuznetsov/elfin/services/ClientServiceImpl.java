package ru.kuznetsov.elfin.services;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.Process;
import io.camunda.zeebe.client.api.response.ProcessInstanceResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kuznetsov.elfin.models.dto.ClientDto;

import java.math.BigInteger;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private static final String BPMN_LOCATION = "src/main/resources/bpmn/ElfinTest.bpmn";
    private final ZeebeClient zeebeClient;

    @Override
    public Boolean gradeClient(ClientDto clientInfo) {
        DeploymentEvent deploymentEvent = deployClientGradeProcess();

        List<Process> processes = deploymentEvent.getProcesses();
        processes.sort(Comparator.comparingInt(Process::getVersion));
        String bpmnProcessId = processes.get(0).getBpmnProcessId();

        ProcessInstanceResult result = createClientGradeInstance(bpmnProcessId, clientInfo);
        return (Boolean) result.getVariablesAsMap().get("pass");
    }

    private DeploymentEvent deployClientGradeProcess() {
        return zeebeClient.newDeployResourceCommand()
                .addResourceFile(BPMN_LOCATION)
                .send()
                .join();

    }

    private ProcessInstanceResult createClientGradeInstance(String bpmnProcessId, ClientDto clientInfo) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("inn", clientInfo.getInn());
        variables.put("region", clientInfo.getRegion());
        variables.put("capital", clientInfo.getCapital());

        return zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(bpmnProcessId)
                .latestVersion()
                .variables(variables)
                .withResult()
                .send()
                .join();
    }
}
