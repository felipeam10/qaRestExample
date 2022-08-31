package com.example.qaRestExample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    // http:localhost:8080/status
    @GetMapping(path = "/status")
    public String statusApi() {
        return "A aplicação está de pernas para o ar";
    }
}
