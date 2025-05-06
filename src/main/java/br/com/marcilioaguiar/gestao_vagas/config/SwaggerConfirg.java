package br.com.marcilioaguiar.gestao_vagas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfirg {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info().title("Gestão de Vagas")
                        .description("API responsável pela gestão de vagas").version("1.0.0"))
                .schemaRequirement("jwt_auth", createSecurityScheme());
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("jwt_auth")
                .in(SecurityScheme.In.HEADER);
    }
}
