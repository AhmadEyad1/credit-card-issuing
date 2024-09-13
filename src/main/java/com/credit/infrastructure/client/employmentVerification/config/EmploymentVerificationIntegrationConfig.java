package com.credit.infrastructure.client.employmentVerification.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "integration.employment-verification")
public class EmploymentVerificationIntegrationConfig {

    private ApiConfig api;
}
