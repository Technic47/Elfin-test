package ru.kuznetsov.elfin.connectors;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.EvaluateDecisionResponse;
import io.camunda.zeebe.client.api.response.ProcessInstanceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kuznetsov.elfin.connectors.contract.CamundaConnector;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CamundaConnectorImpl implements CamundaConnector {

    private final ZeebeClient zeebeClient;

    @Override
    public DeploymentEvent deployProcess(String bpmnPath) {
        return zeebeClient.newDeployResourceCommand()
                .addResourceFile(bpmnPath)
                .send()
                .join();
    }

    @Override
    public ProcessInstanceResult createInstance(String bpmnProcessId, Map<String, Object> variables) {
        return zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(bpmnProcessId)
                .latestVersion()
                .variables(variables)
                .withResult()
                .send()
                .join();
    }

    public EvaluateDecisionResponse evaluateDecision(String decisionId, Map<String, Object> variables) {
        return zeebeClient.newEvaluateDecisionCommand()
                .decisionId(decisionId)
                .variables(variables)
                .send()
                .join();
    }
}
