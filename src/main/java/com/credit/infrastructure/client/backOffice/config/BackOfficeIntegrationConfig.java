package com.credit.infrastructure.client.backOffice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "integration.back-office")
public class BackOfficeIntegrationConfig {

    private ApiConfig api;
}
