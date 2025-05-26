package com.chj.gr.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gr-oauth2-swagger-ms2/protected")
public class Secure2Controller {

    @Operation(summary = "UPDATE secured data", security = @SecurityRequirement(name = "oauth2"))
    @GetMapping("/update")
    @PreAuthorize("hasAuthority('SCOPE_update')")
    public String update() {
        return "SECURED data UPDATE from GR-OAUTH2-SWAGGER-MS2";
    }

    @Operation(summary = "REMOVE secured data", security = @SecurityRequirement(name = "oauth2"))
    @GetMapping("/remove")
    @PreAuthorize("hasAuthority('SCOPE_remove')")
    public String remove() {
        return "SECURED data REMOVE from GR-OAUTH2-SWAGGER-MS2";
    }

    @Operation(summary = "SOME_SCOPE secured data", security = @SecurityRequirement(name = "oauth2"))
    @GetMapping("/somescope")
    @PreAuthorize("hasAuthority('SCOPE_somescope')")
    public String somescope() {
        return "SECURED data SOME_SCOPE from GR-OAUTH2-SWAGGER-MS2";
    }
}
