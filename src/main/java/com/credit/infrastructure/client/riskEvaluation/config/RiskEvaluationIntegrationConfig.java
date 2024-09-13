package com.credit.infrastructure.client.riskEvaluation.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "integration.risk-evaluation")
public class RiskEvaluationIntegrationConfig {

    private ApiConfig api;
}
