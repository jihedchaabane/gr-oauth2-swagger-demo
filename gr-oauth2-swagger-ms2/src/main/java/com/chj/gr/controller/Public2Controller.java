package com.chj.gr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/gr-oauth2-swagger-ms2/public")
public class Public2Controller {

	@Operation(summary = "PUBLIC data")
    @GetMapping("/get")
    public String get() {
        return "PUBLIC data NO_SCOPE from GR-OAUTH2-SWAGGER-MS2";
    }
}
