package com.chj.gr.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "params")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ServiceParamsProperties {

    private Eureka eureka = new Eureka();
    private Oauth2 oauth2 = new Oauth2();
    private GatewayOauth2 gatewayOauth2 = new GatewayOauth2();
    private SwaggerAggregator swaggerAggregator = new SwaggerAggregator();
    private Zipkin zipkin = new Zipkin();
    private Keystore keystore = new Keystore();
    private Truststore truststore = new Truststore();

    public Eureka getEureka() {
        return eureka;
    }

    public void setEureka(Eureka eureka) {
        this.eureka = eureka;
    }

    public Oauth2 getOauth2() {
        return oauth2;
    }

    public void setOauth2(Oauth2 oauth2) {
        this.oauth2 = oauth2;
    }

    public GatewayOauth2 getGatewayOauth2() {
        return gatewayOauth2;
    }

    public void setGatewayOauth2(GatewayOauth2 gatewayOauth2) {
        this.gatewayOauth2 = gatewayOauth2;
    }

    public SwaggerAggregator getSwaggerAggregator() {
		return swaggerAggregator;
	}

	public void setSwaggerAggregator(SwaggerAggregator swaggerAggregator) {
		this.swaggerAggregator = swaggerAggregator;
	}

	public Zipkin getZipkin() {
        return zipkin;
    }

    public void setZipkin(Zipkin zipkin) {
        this.zipkin = zipkin;
    }

    public Keystore getKeystore() {
        return keystore;
    }

    public void setKeystore(Keystore keystore) {
        this.keystore = keystore;
    }

    public Truststore getTruststore() {
        return truststore;
    }

    public void setTruststore(Truststore truststore) {
        this.truststore = truststore;
    }

    // Nested classes
    public static class Eureka {
        private String uri;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }

    public static class Oauth2 {
        private String issuerUri;
        public String getIssuerUri() {
            return issuerUri;
        }

        public void setIssuerUri(String issuerUri) {
            this.issuerUri = issuerUri;
        }
    }

    public static class GatewayOauth2 {
        private String uri;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }

    public static class SwaggerAggregator {
        private String uri;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }

    public static class Zipkin {
        private String uri;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }

    public static class Keystore {
        private String type;
        private String path;
        private String password;
        private String alias;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }
    }

    public static class Truststore {
        private String path;
        private String password;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}