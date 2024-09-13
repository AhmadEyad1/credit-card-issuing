package com.credit.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi(@Value("${springdoc.version}") final String apiVersion, @Value("${springdoc.title}") final String title) {
        return new OpenAPI().info(new Info()
                .title(title)
                .version(apiVersion)
                .description("Flight Booking System"));
    }
}