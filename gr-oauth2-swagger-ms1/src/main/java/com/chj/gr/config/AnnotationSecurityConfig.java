package com.chj.gr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableGlobalMethodSecurity(
		prePostEnabled = true	// enables @preAuthorize and @PostAuathorize
		, securedEnabled = true // enables @secured
		, jsr250Enabled = true	// enables @RolesAllowed (Ensure JSR-250 annotations are enabled)
)
public class AnnotationSecurityConfig {
	
}