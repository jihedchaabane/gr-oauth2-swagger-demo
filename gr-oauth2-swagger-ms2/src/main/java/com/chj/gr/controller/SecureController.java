package com.chj.gr.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secure")
public class SecureController {

    @Operation(summary = "UPDATE secured data", security = @SecurityRequirement(name = "oauth2"))
    @GetMapping("/ms2/update")
    @PreAuthorize("hasAuthority('SCOPE_update')")
    public String update() {
        return "SECURED data UPDATE from MS2";
    }

    @Operation(summary = "REMOVE secured data", security = @SecurityRequirement(name = "oauth2"))
    @GetMapping("/ms2/remove")
    @PreAuthorize("hasAuthority('SCOPE_remove')")
    public String remove() {
        return "SECURED data REMOVE from MS2";
    }

    @Operation(summary = "SOME_SCOPE secured data", security = @SecurityRequirement(name = "oauth2"))
    @GetMapping("/ms2/somescope")
    @PreAuthorize("hasAuthority('SCOPE_somescope')")
    public String somescope() {
        return "SECURED data SOME_SCOPE from MS2";
    }
    
    @Operation(summary = "NO_SCOPE secured data", security = @SecurityRequirement(name = "oauth2"))
    @GetMapping("/ms2/noscope")
//    @PreAuthorize("hasAuthority('SCOPE_noscope')")
    public String noscope() {
        return "SECURED data NO_SCOPE from MS2";
    }
}
