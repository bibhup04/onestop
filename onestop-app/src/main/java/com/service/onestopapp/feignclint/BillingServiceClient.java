package com.service.onestopapp.feignclint;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.service.onestopapp.dto.SubscribeDTO;
import com.service.onestopapp.dto.Subscription;

@FeignClient(name = "ONESTOP-BILLING", url = "http://localhost:8080/subscribe") 
public interface BillingServiceClient {

    @PostMapping("/plan")
    ResponseEntity<String> subscribePlan(@RequestBody SubscribeDTO subscribeDTO);

    @GetMapping("/user/subscription")
    ResponseEntity<Subscription> getSubscribedPlan(@RequestHeader("Authorization") String token);
}
