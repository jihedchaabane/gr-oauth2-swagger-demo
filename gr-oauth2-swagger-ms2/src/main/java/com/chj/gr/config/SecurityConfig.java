package com.chj.gr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                
                .antMatchers("/gr-oauth2-swagger-ms2/public/**").permitAll()
//              .mvcMatchers("/gr-oauth2-swagger-ms2/protected/**").access("hasAuthority('SCOPE_read')")
	              /**
	               * Replaced by com.chj.gr.config.AnnotationSecurityConfig.java
	                 @EnableGlobalMethodSecurity(
							prePostEnabled = true	// enables @PreAuthorize and @PostAuathorize
							, securedEnabled = true // enables @Secured
							, jsr250Enabled = true	// enables @RolesAllowed (Ensure JSR-250 annotations are enabled)
	               */
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }
}
