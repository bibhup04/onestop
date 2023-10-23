package com.service.onestopbilling.controller;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.onestopbilling.dto.GenerateInvoiceDTO;
import com.service.onestopbilling.dto.SubscribeDTO;
import com.service.onestopbilling.dto.UserDTO;
import com.service.onestopbilling.entity.Billing;
import com.service.onestopbilling.entity.CustomDateHandler;
import com.service.onestopbilling.entity.Subscription;
import com.service.onestopbilling.service.BillingService;
import com.service.onestopbilling.service.InvoiceService;
import com.service.onestopbilling.service.SubscriptionService;
import com.service.onestopbilling.service.UserService;

@RestController
@RequestMapping("/subscribe")
public class SubscribeController {
   CustomDateHandler customDateHandler = new CustomDateHandler();

   private final SubscriptionService subscriptionService;

   @Autowired
   private BillingService billingService;

   @Autowired 
   private InvoiceService invoiceService;

   @Autowired
   private UserService userService;

    @Autowired
    public SubscribeController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }



    @PostMapping("/plan")
    public  ResponseEntity<String> subscribePlan(@RequestBody SubscribeDTO subscribeDTO){
        Date endDate = customDateHandler.getEndDate();
        subscriptionService.saveSubscription(endDate, subscribeDTO);
        return new ResponseEntity<>( "plan subscribed successfully", HttpStatus.OK);
    }
   
    @GetMapping("/all")
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        return ResponseEntity.ok().body(subscriptionService.getAllSubscriptions());
    }
    

    //@Scheduled(fixedRate = 10000)
    public void generateBill() {
        billingService.createbills();
       // customDateHandler.increaseEndDateBy30Days();
        subscriptionService.renewSubscription(customDateHandler.getEndDate());
        System.out.println("bill generated and end date updated");
    }

    @GetMapping("/bill")
    public ResponseEntity<List<Billing>> generateNewBill() {
        List<Billing> bills = new ArrayList<>();
        bills = billingService.createbills();
        customDateHandler.increaseEndDateBy30Days();
        subscriptionService.renewSubscription(customDateHandler.getEndDate());
        System.out.println("bill generated and end date updated");
        return ResponseEntity.ok().body(bills);
    }

    @PostMapping("/create-invoice")
    public ResponseEntity<String> createInvoice(@RequestBody List<GenerateInvoiceDTO> generateInvoiceDTO) {
        System.out.println("inside billing controller.");
        return invoiceService.createInvoice(generateInvoiceDTO);
        //return new ResponseEntity<>( "its working", HttpStatus.OK);
    }

    @GetMapping("/user/subscription")
    public ResponseEntity<Subscription> getSubscribedPlan(@RequestHeader("Authorization") String token){
        System.out.println("token is - " + token);
        UserDTO userDTO = userService.getUserDetails(token);
        Subscription subscription = subscriptionService.findSubscriptionByUserId(userDTO.getId());
        return new ResponseEntity<>( subscription, HttpStatus.OK);
    }

    @GetMapping("/user/bill")
    public ResponseEntity<Billing> getUnpaidBill(@RequestHeader("Authorization") String token){
        System.out.println("token is - " + token);
        UserDTO userDTO = userService.getUserDetails(token);
        Billing billing = billingService.findBillByUserIdAndPaymentStatusPending(userDTO.getId());
        return new ResponseEntity<>( billing, HttpStatus.OK);
    }
    
    
}
