package ru.kuznetsov.elfin.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.kuznetsov.elfin.models.dto.ClientDto;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application.yaml")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClientGradeControllerImplTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper om;

    @Test
    void gradeClientOKValuesReturnsTrue() throws Exception {
        performRequestReturn200("123456", 55, new BigInteger("5000000"), "false");
    }

    @Test
    void gradeClientInnIpReturnsFalse() throws Exception {
        performRequestReturn200("123456789012", 55, new BigInteger("5000000"), "true");
    }

    @Test
    void gradeClientProhibitedRegionReturnsFalse() throws Exception {
        performRequestReturn200("123456789", 24, new BigInteger("5000000"), "true");
    }

    @Test
    void gradeClientSmallCapitalReturnsFalse() throws Exception {
        performRequestReturn200("123456789", 24, new BigInteger("4999999"), "true");
    }

    @Test
    void gradeClientNotRfResidentReturnsFalse() throws Exception {
        performRequestReturn200("990956789", 24, new BigInteger("5000000"), "true");
    }

    @Test
    void gradeClientNoInnReturn400() throws Exception {
        Map<String, Object> variables = new HashMap<>();

        variables.put("region", "24");
        variables.put("capital", "2000000");

        performRequestReturn400(variables);
    }

    @Test
    void gradeClientNoRegionReturn400() throws Exception {
        Map<String, Object> variables = new HashMap<>();

        variables.put("inn", "33333");
        variables.put("capital", "2000000");

        performRequestReturn400(variables);
    }

    @Test
    void gradeClientNoCapitalReturn400() throws Exception {
        Map<String, Object> variables = new HashMap<>();

        variables.put("inn", "33333");
        variables.put("region", "24");

        performRequestReturn400(variables);
    }

    private void performRequestReturn200(String inn, Integer region, BigInteger capital, String result) throws Exception {
        ClientDto clientDto = new ClientDto(inn, region, capital);

        mockMvc.perform(request(HttpMethod.POST, "http://localhost:9090/api/v1/client/grade")
                        .content(om.writeValueAsString(clientDto))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString(result)));
    }

    private void performRequestReturn400(Map<String, Object> variables) throws Exception {
        mockMvc.perform(request(HttpMethod.POST, "http://localhost:9090/api/v1/client/grade")
                        .content(om.writeValueAsString(variables))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}