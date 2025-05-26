package com.chj.gr.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/gr-oauth2-swagger-ms1/protected")
public class Secure1Controller {

    @Operation(summary = "READ secured data", security = @SecurityRequirement(name = "oauth2"))
    @GetMapping("/read")
    @PreAuthorize("hasAuthority('SCOPE_read')")
    public String read() {
        return "SECURED data READ from GR-OAUTH2-SWAGGER-MS1";
    }

    @Operation(summary = "WRITE secured data", security = @SecurityRequirement(name = "oauth2"))
    @GetMapping("/write")
    @PreAuthorize("hasAuthority('SCOPE_write')")
    public String write() {
        return "SECURED data WRITE from GR-OAUTH2-SWAGGER-MS1";
    }

    @Operation(summary = "SOME_SCOPE secured data", security = @SecurityRequirement(name = "oauth2"))
    @GetMapping("/somescope")
    @PreAuthorize("hasAuthority('SCOPE_somescope')")
    public String somescope() {
        return "SECURED data SOME_SCOPE from GR-OAUTH2-SWAGGER-MS1";
    }
}
