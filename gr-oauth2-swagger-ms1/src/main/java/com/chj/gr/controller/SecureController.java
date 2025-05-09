package com.chj.gr.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/secure")
public class SecureController {

    @Operation(summary = "READ secured data", security = @SecurityRequirement(name = "oauth2"))
    @GetMapping("/ms1/read")
    @PreAuthorize("hasAuthority('SCOPE_read')")
    public String read() {
        return "SECURED data READ from MS1";
    }

    @Operation(summary = "WRITE secured data", security = @SecurityRequirement(name = "oauth2"))
    @GetMapping("/ms1/write")
    @PreAuthorize("hasAuthority('SCOPE_write')")
    public String write() {
        return "SECURED data WRITE from MS1";
    }

    @Operation(summary = "SOME_SCOPE secured data", security = @SecurityRequirement(name = "oauth2"))
    @GetMapping("/ms1/somescope")
    @PreAuthorize("hasAuthority('SCOPE_somescope')")
    public String somescope() {
        return "SECURED data SOME_SCOPE from MS1";
    }
    
    @Operation(summary = "NO_SCOPE secured data", security = @SecurityRequirement(name = "oauth2"))
    @GetMapping("/ms1/noscope")
//    @PreAuthorize("hasAuthority('SCOPE_noscope')")
    public String noscope() {
        return "SECURED data NO_SCOPE from MS1";
    }
}
