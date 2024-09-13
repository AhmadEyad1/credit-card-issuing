package com.credit.infrastructure.client.identityVerification.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "integration.identity-verification")
public class IdentityVerificationIntegrationConfig {

    private ApiConfig api;
}
