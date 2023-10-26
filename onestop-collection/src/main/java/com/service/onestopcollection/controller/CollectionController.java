package com.service.onestopcollection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
import com.service.onestopcollection.service.OttStubService;
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

    @Autowired
    private OttStubService ottStubService;


    @GetMapping("/user")
    public ResponseEntity<UserDTO> createFamilyDetails(@RequestHeader("Authorization") String token){
        UserDTO userDTO = userService.getUserDetails(token);
        return new ResponseEntity<>( userDTO, HttpStatus.OK);
    }

    @PostMapping("/payment")
    public ResponseEntity<String> receiveCollectionDTO(@RequestHeader("Authorization") String token, @RequestBody CollectionDTO collectionDTO) {

        ottStubService.sendCollectionDTO(collectionDTO);
        collectionService.saveCollection(collectionDTO);
        ResponseEntity<String> response = billingService.updatePaymentStatus(collectionDTO);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());

    }

    @PostMapping("/update/status")
    public ResponseEntity<String> updateSubscriptionStatus(){

        ResponseEntity<String> response = billingService.updateStatus();
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }
    

   // @Scheduled(cron = "0 0 10 14,28 * ?")
    @Scheduled(fixedDelay = 40000, initialDelay = 40000)
    public void taskFor14thAnd28th() {
        ResponseEntity<String> response = billingService.updateStatus();
        System.out.println("\n\nstatus updated successfully\n\n");
       System.out.println(response.getBody());
    }
}
