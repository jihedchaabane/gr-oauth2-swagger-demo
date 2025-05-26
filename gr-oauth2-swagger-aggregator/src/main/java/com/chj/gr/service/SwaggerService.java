package com.chj.gr.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.discovery.EurekaClient;

@Service
public class SwaggerService {
	
    private static final Logger logger = LoggerFactory.getLogger(SwaggerService.class);
    
    private final EurekaClient eurekaClient;
    private final RestTemplate restTemplate;

    public SwaggerService(EurekaClient eurekaClient, @Qualifier("restTemplate") RestTemplate restTemplate) {
		this.eurekaClient = eurekaClient;
		this.restTemplate = restTemplate;
	}

	public List<SwaggerResource> getSwaggerResources() {
		logger.info("***********************************************************************************");
        List<SwaggerResource> resources = new ArrayList<>();
        var applications = eurekaClient.getApplications();
        if (applications == null || applications.getRegisteredApplications().isEmpty()) {
            logger.warn("No applications found in Eureka registry");
            return resources;
        }
        /**
         * HTTP GET http://localhost:8761/eureka/apps/
         */
        eurekaClient.getApplications().getRegisteredApplications().forEach(app -> {
        	logger.info("Processing application: {}", app.getName());
            app.getInstances().forEach(instance -> {
            	logger.info(instance.toString());
            	/**
            	 * Vérifier la métadonnée swagger.aggregator.enabled:
            	 * eureka:
					  instance:
					    metadata-map:
					      swagger.aggregator.enabled: false/true ?
            	 */
                String swaggerEnabled = instance.getMetadata().getOrDefault("swagger.aggregator.enabled", "false");
                if (!"true".equalsIgnoreCase(swaggerEnabled)) {
                    logger.info("Skipping service {}: swagger.aggregator.enabled={}", app.getName(), swaggerEnabled);
                    logger.info("-----------------------------------------------------------------------------------");
                    return;
                }
                
				String[] possibleEndpoints = {
						String.format("http://%s:%s/v3/api-docs", instance.getHostName(), instance.getPort()),
						String.format("http://%s:%s/v2/api-docs", instance.getHostName(), instance.getPort())
				};
				for (String swaggerUrl : possibleEndpoints) {
				    try {
				    	logger.info("Attempting to fetch Swagger JSON from: {}", swaggerUrl);
				        String swaggerJson = restTemplate.getForObject(swaggerUrl, String.class);
				        if (swaggerJson != null) {
				            resources.add(new SwaggerResource(app.getName(), swaggerUrl));
				            logger.info("Successfully retrieved Swagger JSON for service: {} at {}", app.getName(), swaggerUrl);
				            break;
				        } else {
                            logger.warn("Swagger JSON is null for service: {} at {}", app.getName(), swaggerUrl);
                        }
				    } catch (Exception e) {
				        logger.error("Failed to retrieve Swagger JSON for service: {} at {}. Error: {}", 
				            app.getName(), swaggerUrl, e.getMessage());
				    }
				}
				logger.info("-----------------------------------------------------------------------------------");
            });
        });
        logger.info("Returning {} Swagger resources", resources.size());
        return resources;
    }

    public static class SwaggerResource {
        private final String name;
        private final String url;

        public SwaggerResource(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }
}