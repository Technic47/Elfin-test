package ru.kuznetsov.elfin.config;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpringConfig {

    private final CamundaConnection camundaConnection;

    @Bean
    public ZeebeClient getZeebeClient(OAuthCredentialsProvider credentialsProvider) {
        try {
            ZeebeClient client = ZeebeClient.newClientBuilder()
                    .gatewayAddress(camundaConnection.getGateWayAddress() + ":" + camundaConnection.getGateWayPort())
                    .usePlaintext()
//                    .grpcAddress(URI.create(camundaConnection.getGrpc() + ":" + camundaConnection.getGrpcPort()))
//                    .restAddress(URI.create(camundaConnection.getRest() + ":" + camundaConnection.getRestPort()))
//                    .credentialsProvider(credentialsProvider)
                    .build();

            client.newTopologyRequest().send().join();
            return client;
        } catch (Exception e) {
            System.out.println("Zeebe client creation failed");
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Bean
    public OAuthCredentialsProvider getOauthCredentialsProvider() {
        return new OAuthCredentialsProviderBuilder()
                .authorizationServerUrl(camundaConnection.getOAuthAPI())
                .audience(camundaConnection.getAudience())
                .clientId(camundaConnection.getClientId())
                .clientSecret(camundaConnection.getClientSecret())
                .build();
    }
}
