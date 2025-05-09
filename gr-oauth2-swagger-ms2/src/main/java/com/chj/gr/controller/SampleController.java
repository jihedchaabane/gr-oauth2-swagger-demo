package com.chj.gr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api")
public class SampleController {

    @Operation(summary = "Get sample data", security = @SecurityRequirement(name = "oauth2"))
    @GetMapping("/data")
    public String getData() {
        return "Sample data from MS2";
    }
}
