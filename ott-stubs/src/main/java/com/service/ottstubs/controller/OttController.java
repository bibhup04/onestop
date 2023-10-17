package com.service.ottstubs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ott")
public class OttController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World";
    }
    
}
