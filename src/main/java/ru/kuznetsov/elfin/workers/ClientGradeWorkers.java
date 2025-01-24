package ru.kuznetsov.elfin.workers;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class ClientGradeWorkers {

    private static final String NOT_RF_RESIDENT_INN_START = "9909";
    private static final Integer BLOCK_REGION = 24;
    private static final BigInteger CAPITAL_LOW_LIMIT = new BigInteger("5000000");

//    @JobWorker(type = "clientGrade")
    public void clientGrade(final JobClient client, final ActivatedJob job) {
//        Map<String, Object> variables = job.getVariablesAsMap();
//
//        String inn = (String) variables.get("inn");
//        String innStart = inn.substring(0, 3);
//        Integer region = (Integer) variables.get("region");
//        BigInteger capital = (BigInteger) variables.get("capital");
//
//        boolean result = !innStart.equals(NOT_RF_RESIDENT_INN_START) &&
//                inn.length() != 12 &&
//                !Objects.equals(region, BLOCK_REGION) &&
//                capital.compareTo(CAPITAL_LOW_LIMIT) < 0;
//
//        variables.put("result", result);
//
//        client.newCompleteCommand(job.getKey())
//                .variables(variables)
//                .send()
//                .exceptionally((throwable -> {
//                    throw new RuntimeException("Could not complete job", throwable);
//                }));
//
//        log.info("ClientGradeDelegate executed");
    }
}
