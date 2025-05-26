package com.chj.gr.properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "swagger-params")
public class SwaggerParamsProperties {
	private String issuerUri;
	private Servers servers = new Servers();
	private Aggregator aggregator = new Aggregator();

	public String getIssuerUri() {
		return issuerUri;
	}

	public void setIssuerUri(String issuerUri) {
		this.issuerUri = issuerUri;
	}

	public Servers getServers() {
		return servers;
	}

	public void setServers(Servers servers) {
		this.servers = servers;
	}

	public Aggregator getAggregator() {
		return aggregator;
	}

	public void setAggregator(Aggregator aggregator) {
		this.aggregator = aggregator;
	}

	public static class Servers {
		private List<Server> liste = new ArrayList<>();

		public List<Server> getListe() {
			return liste;
		}

		public void setListe(List<Server> liste) {
			this.liste = liste;
		}
	}

	public static class Server {
		private String uri;
		private String description;

		public String getUri() {
			return uri;
		}

		public void setUri(String uri) {
			if (StringUtils.isNotEmpty(uri)) {
				uri = uri.contains(":84") ? uri.replace("http", "https") : uri;
			}
			this.uri = uri;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}

	public static class Aggregator {
		private boolean enabled;
		private Cors cors = new Cors();

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public Cors getCors() {
			return cors;
		}

		public void setCors(Cors cors) {
			this.cors = cors;
		}
	}

	public static class Cors {
		private String uri;

		public String getUri() {
			return uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}
	}
}