package ru.kuznetsov.elfin.connectors.contract;

import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceResult;

import java.util.Map;

public interface CamundaConnector {

    DeploymentEvent deployProcess(String bpmnPath);

    ProcessInstanceResult createInstance(String bpmnProcessId, Map<String, Object> variables);
}
