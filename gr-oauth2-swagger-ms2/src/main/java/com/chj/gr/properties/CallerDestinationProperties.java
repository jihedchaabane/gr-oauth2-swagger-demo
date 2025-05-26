package com.chj.gr.properties;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "caller")
public class CallerDestinationProperties {
	
	private Map<String, DestinationClient> destination = new HashMap<>();

	public Map<String, DestinationClient> getDestination() {
		return destination;
	}
	public void setDestination(Map<String, DestinationClient> destination) {
		this.destination = destination;
	}
	
	public DestinationClient getDestinationClient(String key) {
		Optional<DestinationClient> opClient = Optional.of(this.destination.get(key));
		return opClient.isPresent() ? opClient.get() : null;
	}
	
	public boolean isSecuredClient(String key) {
		DestinationClient destinationClient = getDestinationClient(key);
		if (   destinationClient != null
			&& StringUtils.isNotEmpty(destinationClient.getClientId())
			&& StringUtils.isNotEmpty(destinationClient.getClientSecret())
			&& StringUtils.isNotEmpty(destinationClient.getRegistrationId())
			&& StringUtils.isNotEmpty(destinationClient.getScopes())) {
			return true;
		}
		return false;
	}
	
	public io.swagger.v3.oas.models.security.Scopes getSwaggerScopes() {
		io.swagger.v3.oas.models.security.Scopes swaggerScopes = null;
		DestinationClient destinationClient = getDestinationClient("client0");
		if (destinationClient != null) {
			for (Iterator<String> iterator = Arrays.asList(
					destinationClient.getScopes().split(",")).iterator(); iterator.hasNext();) {
				String scope = iterator.next();
				if (swaggerScopes == null) {
					swaggerScopes = new io.swagger.v3.oas.models.security.Scopes();
				}
				swaggerScopes.addString(scope, destinationClient.getRegistrationId());
			}
		}
		return swaggerScopes;
	}
	
	public static class DestinationClient {
		private String resourceUri;
		private String clientId;
		private String clientSecret;
		private String scopes;
		private String registrationId;
		
		public DestinationClient() {
			super();
		}

		public String getResourceUri() {
			return resourceUri;
		}

		public void setResourceUri(String resourceUri) {
			this.resourceUri = resourceUri;
		}

		public String getClientId() {
			return clientId;
		}

		public void setClientId(String clientId) {
			this.clientId = clientId;
		}

		public String getClientSecret() {
			return clientSecret;
		}

		public void setClientSecret(String clientSecret) {
			this.clientSecret = clientSecret;
		}

		public String getScopes() {
			return scopes;
		}

		public void setScopes(String scopes) {
			this.scopes = scopes;
		}

		public String getRegistrationId() {
			return registrationId;
		}

		public void setRegistrationId(String registrationId) {
			this.registrationId = registrationId;
		}
	}
}