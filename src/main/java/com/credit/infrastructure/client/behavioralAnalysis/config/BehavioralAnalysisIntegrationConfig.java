package com.credit.infrastructure.client.behavioralAnalysis.config;

import com.credit.infrastructure.client.backOffice.config.ApiConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "integration.behavioral-analysis")
public class BehavioralAnalysisIntegrationConfig {

    private ApiConfig api;
}