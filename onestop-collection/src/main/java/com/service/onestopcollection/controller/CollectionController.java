package com.service.onestopcollection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.converters.Auto;
import com.service.onestopcollection.DTO.CollectionDTO;
import com.service.onestopcollection.DTO.UserDTO;
import com.service.onestopcollection.service.BillingService;
import com.service.onestopcollection.service.CollectionService;
import com.service.onestopcollection.service.UserService;

@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    private UserService userService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private BillingService billingService;

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO> createFamilyDetails(@RequestHeader("Authorization") String token){
        System.out.println("token is - " + token);
        UserDTO userDTO = userService.getUserDetails(token);
        return new ResponseEntity<>( userDTO, HttpStatus.OK);
    }

    @PostMapping("/payment")
    public ResponseEntity<String> receiveCollectionDTO(@RequestHeader("Authorization") String token, @RequestBody CollectionDTO collectionDTO) {
        System.out.println("token is - " + token);
        System.out.println("Received CollectionDTO:");
        System.out.println("Subscription ID: " + collectionDTO.getSubscriptionId());
        System.out.println("User ID: " + collectionDTO.getUserId());
        System.out.println("Amount Collected: " + collectionDTO.getAmountCollected());
        System.out.println("Bill ID: " + collectionDTO.getBillId());

        collectionService.saveCollection(collectionDTO);
        ResponseEntity<String> response = billingService.updatePaymentStatus(collectionDTO);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }
    
}
