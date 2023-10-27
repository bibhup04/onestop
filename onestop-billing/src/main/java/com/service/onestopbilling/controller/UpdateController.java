package com.service.onestopbilling.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.onestopbilling.dto.CollectionDTO;
import com.service.onestopbilling.service.BillingService;
import com.service.onestopbilling.service.SubscriptionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/subscribe")
@Tag(name = "Update controller", description = "It updates the account and payment status.")
public class UpdateController {

    @Autowired
    private BillingService billingService;

    @Autowired
    private SubscriptionService subscriptionService;

    
    /** 
     * @param collectionDTO
     * @return ResponseEntity<String>
     */
    @Operation(summary = "Update Payment Status", description = "This method recieves the details of user and update payment status to paid.")
    @PostMapping("/update/payment")
    public ResponseEntity<String> receiveCollectionDTO(@RequestBody CollectionDTO collectionDTO) {

        System.out.println("Subscription ID: " + collectionDTO.getSubscriptionId());
        System.out.println("Bill ID: " + collectionDTO.getBillId());

        try {
            billingService.updatePaymentStatusToPaid(collectionDTO.getBillId());
            subscriptionService.updateStatusToActive(collectionDTO.getSubscriptionId());
            return new ResponseEntity<>("Status updated", HttpStatus.OK);
        } catch (IllegalArgumentException e) {            
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while updating status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     @Operation(summary = "Update Account Status", description = "This method get called periodically and account status from Active to Suspend, Terminate based on payment status.")
    @PostMapping("/update/status")
    public ResponseEntity<String> updateStatus(){
        List<Long> subscriptionId = billingService.findSubscriptionIdsByPaymentStatusPending();
        subscriptionService.updateStatusForSubscriptions(subscriptionId);
        return new ResponseEntity<>("Status updated", HttpStatus.OK);
    }
    
}
