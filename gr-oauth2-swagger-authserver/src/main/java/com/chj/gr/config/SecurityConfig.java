package com.chj.gr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;
@EnableWebSecurity
public class SecurityConfig {

	private CorsConfigurationSource corsConfigurationSource;
	
    public SecurityConfig(CorsConfigurationSource corsConfigurationSource) {
		this.corsConfigurationSource = corsConfigurationSource;
	}


	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers("/oauth2/token").permitAll()
                                .anyRequest().permitAll()
                );
        return http.build();
    }

}