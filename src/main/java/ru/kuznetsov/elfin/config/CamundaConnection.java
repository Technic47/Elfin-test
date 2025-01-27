package ru.kuznetsov.elfin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "camunda-connection")
public class CamundaConnection {

    private String gateWayAddress;
    private String gateWayPort;
    private String grpc;
    private String grpcPort;
    private String rest;
    private String restPort;
    private String oAuthAPI;
    private String audience;
    private String clientId;
    private String clientSecret;
    private String elasticSearchHost;
    private String elasticSearchPort;
}
