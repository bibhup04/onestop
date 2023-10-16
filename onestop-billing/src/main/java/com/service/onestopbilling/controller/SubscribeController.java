package com.service.onestopbilling.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/billing")
public class SubscribeController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World";
    }

    @Scheduled(fixedRate = 10000) // Run every 10 seconds (30000 milliseconds)
    public void printHelloWorld() {
        System.out.println("Hello World");
    }
    
}
