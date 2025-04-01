package com.example.tipovacka.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.servers.Server;
import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import java.util.Map;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI tipovackaApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tipovačka API")
                        .description("REST API pro správu tipovací soutěže")
                        .version("1.0.0"))
                .addServersItem(new Server().url("http://localhost:9000"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .extensions(Map.of(
                    "x-encoding", StandardCharsets.UTF_8.name(),
                    "x-content-type", MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"
                ));
    }
} 