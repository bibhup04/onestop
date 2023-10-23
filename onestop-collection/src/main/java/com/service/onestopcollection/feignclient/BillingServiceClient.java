package com.service.onestopcollection.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.service.onestopcollection.DTO.CollectionDTO;

@FeignClient(name = "ONESTOP-BILLING", url = "http://localhost:8080/subscribe")
public interface BillingServiceClient {

    @PostMapping("/update/payment")
    ResponseEntity<String> receiveCollectionDTO(@RequestBody CollectionDTO collectionDTO);
    
}
