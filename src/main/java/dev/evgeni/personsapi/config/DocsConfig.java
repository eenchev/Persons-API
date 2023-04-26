package dev.evgeni.personsapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class DocsConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI(SpecVersion.V31)
            .components(new Components()
            .addSecuritySchemes("bearer-key",
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT"))
        );

    }
    
}
