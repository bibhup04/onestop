package com.service.ottstubs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.ottstubs.DTO.CollectionDTO;
import com.service.ottstubs.DTO.StubDTO;

@RestController
@RequestMapping("/ott")
public class OttController {

    
    /** 
     * @return String
     */
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

    @PostMapping("/collection")
    public ResponseEntity<String> receiveCollectionDTO(@RequestBody CollectionDTO collectionDTO) {
        
        System.out.println("user id - " + collectionDTO.getUserId());
        System.out.println("Collected amount- " + collectionDTO.getAmountCollected());

        return new ResponseEntity<>("Amount collected", HttpStatus.OK);
   
    }
    
}
