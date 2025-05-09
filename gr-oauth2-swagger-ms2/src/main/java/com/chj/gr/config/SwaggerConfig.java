package com.chj.gr.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
	@Value("${app.openapi.local-url}")
	private String localUrl;
	
	@Value("${app.openapi.dev-url}")
	private String devUrl;

	@Value("${app.openapi.homol-url}")
	private String homolUrl;

	@Value("${app.openapi.prod-url}")
	private String prodUrl;

	@Value("${spring.application.name}")
	private String artifact;

    @Bean
    public OpenAPI customOpenAPI() {
    	
    	Server localServer = new Server();
		localServer.setUrl(localUrl);
		localServer.setDescription("Server URL in Local environment");
		
		Server devServer = new Server();
		devServer.setUrl(devUrl);
		devServer.setDescription("Server URL in Development environment");

		Server homolServer = new Server();
		homolServer.setUrl(homolUrl);
		homolServer.setDescription("Server URL in Homologation environment");

		Server prodServer = new Server();
		prodServer.setUrl(prodUrl);
		prodServer.setDescription("Server URL in Production environment");

		Contact contact = new Contact();
		contact.setEmail("jihed@gmail.com");
		contact.setName("Jihed");
		contact.setUrl("https://www.jihed.com");

		License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

		Info info = new Info().title("Swagger Management API").version("0.0.1-SNAPSHOT").contact(contact)
				.description("This API exposes endpoints to manage " + artifact.toUpperCase() + ".")
				.termsOfService("https://www.jihed.com").license(mitLicense);

        return new OpenAPI()
        		.info(info)
        		.servers(List.of(localServer, devServer, homolServer, prodServer))
                .components(new Components()
                        .addSecuritySchemes("oauth2", new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new OAuthFlows()
                                        .clientCredentials(new OAuthFlow()
                                                .tokenUrl("http://localhost:8764/oauth2/token")
                                                .scopes(new io.swagger.v3.oas.models.security.Scopes()
                                                        .addString("update", "update scope")
                                                        .addString("remove", "remove scope"))))))
                .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList("oauth2"));
    }
}