package com.credit.infrastructure.client.creditCardProcessing.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "integration.credit-card-processing")
public class CreditCardProcessingIntegrationConfig {

    private ApiConfig api;
}
