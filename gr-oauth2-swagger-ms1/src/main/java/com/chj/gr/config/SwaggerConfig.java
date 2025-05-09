package com.chj.gr.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("oauth2", new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new OAuthFlows()
                                        .authorizationCode(new OAuthFlow()
                                                .authorizationUrl("http://localhost:9000/oauth2/authorize")
                                                .tokenUrl("http://localhost:9000/oauth2/token")
                                                .scopes(new io.swagger.v3.oas.models.security.Scopes()
                                                        .addString("read", "read scope")
                                                        .addString("write", "write scope"))))))
                .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList("oauth2"));
    }
}