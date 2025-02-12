package ru.kuznetsov.elfin.config;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpringConfig {

    @Bean
    public DmnEngine getDmnEngine() {
        return DmnEngineConfiguration
                .createDefaultDmnEngineConfiguration()
                .buildEngine();
    }
}
