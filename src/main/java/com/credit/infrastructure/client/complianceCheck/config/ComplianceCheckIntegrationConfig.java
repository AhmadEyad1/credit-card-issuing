package com.credit.infrastructure.client.complianceCheck.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "integration.compliance-check")
public class ComplianceCheckIntegrationConfig {

    private ApiConfig api;
}
