package com.chj.gr.config.swagger;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chj.gr.properties.CallerDestinationProperties;
import com.chj.gr.properties.SwaggerParamsProperties;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

/**
 * https://www.bezkoder.com/spring-boot-swagger-3/
 * https://github.com/bezkoder/spring-boot-swagger-3-example/tree/master
 * 
 * https://www.baeldung.com/java-spring-security-permit-swagger-ui
 */
@Configuration
public class OpenAPIConfig {

	private BuildProperties buildProperties;
	private SwaggerParamsProperties swaggerParamsProperties;
	private CallerDestinationProperties callerDestinationProperties;

	public OpenAPIConfig(BuildProperties buildProperties, CallerDestinationProperties callerDestinationProperties, SwaggerParamsProperties swaggerParamsProperties) {
		this.buildProperties = buildProperties;
		this.callerDestinationProperties = callerDestinationProperties;
		this.swaggerParamsProperties = swaggerParamsProperties;
	}

	@Bean
	public OpenAPI myOpenAPI() {
		List<Server> servers = null;
		if (swaggerParamsProperties != null && swaggerParamsProperties.getServers() != null) {
			servers = swaggerParamsProperties.getServers().getListe().stream()
					.map(s -> {
						Server server = new Server();
						server.setUrl(s.getUri());
						server.setDescription(s.getDescription());
						return server; 
					}).collect(Collectors.toList());
		}
		Contact contact = new Contact();
		contact.setEmail("jihed@gmail.com");
		contact.setName("Jihed");
		contact.setUrl("https://www.jihed.com");

		License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

		Info info = new Info()
				.title("Swagger Management API")
				.version(buildProperties.getVersion())
				.contact(contact)
				.description("This API exposes endpoints to manage " + buildProperties.getName().toUpperCase() + ".")
				.termsOfService("https://www.jihed.com")
				.license(mitLicense);
		
		OpenAPI openAPI = new OpenAPI()
				.info(info)
				.servers(servers);
		if (callerDestinationProperties.isSecuredClient("client0")) {
			openAPI
			.addSecurityItem(new SecurityRequirement().addList("oauth2"))
            .components(new Components()
                .addSecuritySchemes("oauth2", 
                		new SecurityScheme()
                			.type(SecurityScheme.Type.OAUTH2)
                			.flows(new OAuthFlows()
                					.clientCredentials(new OAuthFlow()
                							.tokenUrl(swaggerParamsProperties.getIssuerUri()
                													.concat("/oauth2/token"))
                							.scopes(callerDestinationProperties.getSwaggerScopes())
                					)
                			)
                )
            );
		}
		
	    return openAPI;
	            
	}
}
