package com.service.onestopbilling.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.onestopbilling.dto.SubscribeDTO;
import com.service.onestopbilling.entity.CustomDateHandler;
import com.service.onestopbilling.service.BillingService;
import com.service.onestopbilling.service.SubscriptionService;

@RestController
@RequestMapping("/subscribe")
public class SubscribeController {
   CustomDateHandler customDateHandler = new CustomDateHandler();

   private final SubscriptionService subscriptionService;
   private final BillingService billingService;

    @Autowired
    public SubscribeController(SubscriptionService subscriptionService, BillingService billingService) {
        this.subscriptionService = subscriptionService;
        this.billingService = billingService;
    }



    @PostMapping("/plan")
    public  ResponseEntity<String> subscribePlan(@RequestBody SubscribeDTO subscribeDTO){
        Date endDate = customDateHandler.getEndDate();
        subscriptionService.saveSubscription(endDate, subscribeDTO);
        return new ResponseEntity<>( "plan subscribed successfully", HttpStatus.OK);
    }
   

    
    @GetMapping("/hello")
    public String helloWorld() {
        
        System.out.println("date is - " + customDateHandler.getEndDate());
        return "Hello World";
    }

    //@Scheduled(fixedRate = 10000)
    public void printHelloWorld() {
        billingService.createbills();
        customDateHandler.increaseEndDateBy30Days();
        subscriptionService.renewSubscription(customDateHandler.getEndDate());
        System.out.println("bill generated and end date updated");
    }
    
}
