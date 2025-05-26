package com.chj.gr.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.UUID;

@Configuration
public class AuthorizationServerConfig {
    /**
     * Securing OPTIONS /oauth2/token indique que le serveur reçoit une requête OPTIONS, qui est une requête CORS preflight envoyée
     * par le navigateur (via Swagger UI) avant la requête POST.
     * 
     * Swagger UI, exécuté dans le navigateur (sur http://localhost:8081 ou http://localhost:8082), envoie une requête CORS vers http://localhost:8764/oauth2/token. 
     * 
     * Si le serveur d'autorisation ne retourne pas les en-têtes CORS appropriés (comme Access-Control-Allow-Origin), 
     * 		le navigateur rejette la réponse, entraînant une erreur 403.
     * 
     * Spring Security ou le serveur d'autorisation peut ne pas gérer correctement les requêtes OPTIONS, qui sont nécessaires pour les requêtes CORS:
     * 		donc, ci dessous la configuration correspondante.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        /**
         *  WORKS FINE.
         */
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowCredentials(false); // Nécessaire pour le motif "*"
        /** */
        /**
         * WORKS FINE TOO.
         */
//        configuration.setAllowedOriginPatterns(Arrays.asList("[*]"));
//        configuration.setAllowCredentials(false); // Nécessaire pour le motif "[*]"
        /** */
        /**
         * WORKS FINE TOO.
         */
//        configuration.setAllowedOrigins(Arrays.asList(
//        		"http://localhost:8081", 	// swagger Authorize : gr-oauth2-swagger-ms1
//        		"http://localhost:8082",	// swagger Authorize : gr-oauth2-swagger-ms2
//        		"http://localhost:8765" 	// swagger Authorize : gr-conf-swagger-aggregator
//        ));
//        configuration.setAllowCredentials(true);
        /** */
        /**
         * WORKS FINE TOO.
         */
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:*"));
        configuration.setAllowCredentials(true); // Nécessaire pour le motif "http://localhost:*"
        /** */
        
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(400, "Authentication error: " + authException.getMessage());
                        }))
                .build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient1 = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("gr-oauth2-swagger-ms1")
                .clientSecret("{noop}swagger-ms1")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("read")
                .scope("write")
                .build();
        
        RegisteredClient registeredClient2 = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("gr-oauth2-swagger-ms2")
                .clientSecret("{noop}swagger-ms2")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("update")
                .scope("remove")
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient1, registeredClient2);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = generateRsaKey();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    private static RSAKey generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder()
                .issuer("http://localhost:8764")
                .build();
    }
}