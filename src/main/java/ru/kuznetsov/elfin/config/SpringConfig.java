package ru.kuznetsov.elfin.config;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class SpringConfig {

    private static final String gateWayAddress = "localhost:26500";
    private static final String zeebeGrpc = "localhost:26500";
    private static final String zeebeRest = "localhost:26500";
    private static final String oAuthAPI = "http://localhost:18080/auth/realms/camunda-platform/protocol/openid-connect/token";
    private static final String audience = "zeebe-api";
    private static final String clientId = "zeebe";
    private static final String clientSecret = "zecret";

    @Bean
    public ZeebeClient getZeebeClient(OAuthCredentialsProvider credentialsProvider) {
        try {
            ZeebeClient client = ZeebeClient.newClientBuilder()
                    .gatewayAddress(gateWayAddress)
                    .usePlaintext()
//                    .grpcAddress(URI.create(zeebeGrpc))
//                    .restAddress(URI.create(zeebeRest))
//                    .credentialsProvider(credentialsProvider)
                    .build();

            client.newTopologyRequest().send().join();
            return client;
        } catch (Exception e){
            System.err.println("Zeebe client creation failed");
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Bean
    public OAuthCredentialsProvider getOauthCredentialsProvider() {
        return new OAuthCredentialsProviderBuilder()
                .authorizationServerUrl(oAuthAPI)
                .audience(audience)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }
}
