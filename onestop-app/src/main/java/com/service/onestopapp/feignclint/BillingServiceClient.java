package com.service.onestopapp.feignclint;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.service.onestopapp.dto.SubscribeDTO;

@FeignClient(name = "ONESTOP-BILLING", url = "http://localhost:8080/subscribe") 
public interface BillingServiceClient {

    @PostMapping("/plan")
    ResponseEntity<String> subscribePlan(@RequestBody SubscribeDTO subscribeDTO);
}
