package com.service.onestopbilling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.onestopbilling.dto.CollectionDTO;
import com.service.onestopbilling.service.BillingService;

@RestController
@RequestMapping("/subscribe")
public class UpdateController {

    @Autowired
    private BillingService billingService;

    @PostMapping("/update/payment")
    public ResponseEntity<String> receiveCollectionDTO(@RequestBody CollectionDTO collectionDTO) {
        System.out.println("Received CollectionDTO:");
        System.out.println("Subscription ID: " + collectionDTO.getSubscriptionId());
        System.out.println("User ID: " + collectionDTO.getUserId());
        System.out.println("Amount Collected: " + collectionDTO.getAmountCollected());
        System.out.println("Bill ID: " + collectionDTO.getBillId());

        billingService.updatePaymentStatusToPaid(collectionDTO.getBillId());

        return new ResponseEntity<>( "Status updated", HttpStatus.OK);
    }
    
}
