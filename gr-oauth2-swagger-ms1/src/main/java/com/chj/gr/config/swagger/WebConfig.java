package com.chj.gr.config.swagger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.chj.gr.properties.SwaggerParamsProperties;


@Configuration
public class WebConfig implements WebMvcConfigurer {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private SwaggerParamsProperties swaggerParamsProperties;
	
    public WebConfig(SwaggerParamsProperties swaggerParamsProperties) {
		this.swaggerParamsProperties = swaggerParamsProperties;
	}

	@Override
    public void addCorsMappings(CorsRegistry registry) {
		logger.info("Allowed Origins : {}", swaggerParamsProperties.getAggregator().getCors().getUri());
		logger.info("Allowed Methods : GET, POST, PUT, DELETE, OPTIONS");
		logger.info("Allowed Headers : \"*\"");
		logger.info("Allowed Credentials : true");
		
        registry.addMapping("/**")
                .allowedOrigins(swaggerParamsProperties.getAggregator().getCors().getUri())
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
