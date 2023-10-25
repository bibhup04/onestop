package com.service.ottstubs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.ottstubs.DTO.StubDTO;

@RestController
@RequestMapping("/ott")
public class OttController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World";
    }

    @PostMapping("/stub")
    public String receiveStubDTO(@RequestBody StubDTO stubDTO) {
        System.out.println("Received PlanDTO: " + stubDTO.getOtts().get(0).getOttName());
        System.out.println("Received UserDTO: " + stubDTO.getUserDTO());
        System.out.println("Received Members: " + stubDTO.getMembers());
        return "StubDTO received successfully!";
    }
    
}
