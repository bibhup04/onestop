package com.service.onestopbilling.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/subscribe")
@Tag(name = "Subscription controller", description = "It keeps record of subscription and create bills")
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


    
    /** 
     * @param subscribeDTO
     * @return ResponseEntity<String>
     */
    @Operation(summary = "Subscribe plans", description = "Recieves user and plan details and add those details to Database.")
    @PostMapping("/plan")
    public ResponseEntity<String> subscribePlan(@RequestBody SubscribeDTO subscribeDTO) {
        boolean subscribed = subscriptionService.isSubscriptionPresentForUser(subscribeDTO.getUserId());
        Optional<Billing> billing = billingService.findBillByUserIdAndPaymentStatusPending(subscribeDTO.getUserId());
        if(billing.isPresent()){
            return new ResponseEntity<>("Kindly clear any pending payments before proceeding.", HttpStatus.BAD_REQUEST);
        }else if (subscribed) {
            return new ResponseEntity<>("You are currently enrolled in a subscription plan.", HttpStatus.BAD_REQUEST);
        } else {
            Date endDate = customDateHandler.getEndDate();
            subscriptionService.saveSubscription(endDate, subscribeDTO);
            return new ResponseEntity<>("Plan subscribed successfully", HttpStatus.OK);
        }
    }
   
    @GetMapping("/all")
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        return ResponseEntity.ok().body(subscriptionService.getAllSubscriptions());
    }
    


        //@Scheduled(cron = "*/2 * * * *")
    @Scheduled(fixedDelay = 120000, initialDelay = 120000)
    public void generateBill() {
        billingService.createbills();
       // customDateHandler.increaseEndDateBy30Days();
        subscriptionService.renewSubscription(customDateHandler.getEndDate());
        System.out.println("\n\nbill generated and end date updated\n\n");
    }

    @Operation(summary = "Generate Bills", description = "This method generates a bill when a user ends subscription manually.")
    @GetMapping("/bill")
    public ResponseEntity<List<Billing>> generateNewBill() {
        List<Billing> bills = new ArrayList<>();
        bills = billingService.createbills();
        customDateHandler.increaseEndDateBy30Days();
        subscriptionService.renewSubscription(customDateHandler.getEndDate());
        System.out.println("bill generated and end date updated");
        return ResponseEntity.ok().body(bills);
    }

    @Operation(summary = "Create Invoice", description = "This method sends generateInvoiceDTO to invoice service to generate a invoice.")
    @PostMapping("/create-invoice")
    public ResponseEntity<String> createInvoice(@RequestBody List<GenerateInvoiceDTO> generateInvoiceDTO) {
        System.out.println("inside billing controller.");
        return invoiceService.createInvoice(generateInvoiceDTO);
        //return new ResponseEntity<>( "its working", HttpStatus.OK);
    }

    @Operation(summary = "Display Subscription", description = "This method displays the subscribed plan of the loggedin user.")
    @GetMapping("/user/subscription")
    public ResponseEntity<Subscription> getSubscribedPlan(@RequestHeader("Authorization") String token){
        System.out.println("token is - " + token);
        UserDTO userDTO = userService.getUserDetails(token);
        Subscription subscription = subscriptionService.findSubscriptionByUserId(userDTO.getId());
        return new ResponseEntity<>( subscription, HttpStatus.OK);
    }

    @Operation(summary = "End Subscription", description = "This method ends the subscription for given user.")
    @PostMapping("/end/subscription")
    public ResponseEntity<String> endSubscribedPlan(@RequestBody Subscription subscription){
        System.out.println("subscription id - " + subscription.getSubscriptionId());
        return billingService.endSubscriptionBill(subscription);
        //return new ResponseEntity<>( "subscription ended", HttpStatus.OK);
    }

    @Operation(summary = "Display Bill", description = "This method displays the last bill of the user.")
    @GetMapping("/user/bill")
    public ResponseEntity<Billing> getUnpaidBill(@RequestHeader("Authorization") String token){
        //System.out.println("token is - " + token);
        UserDTO userDTO = userService.getUserDetails(token);
        Billing billing = billingService.getLastBillByUserId(userDTO.getId());
        return new ResponseEntity<>( billing, HttpStatus.OK);
    }
}
