package com.service.onestopcollection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.service.onestopcollection.DTO.CollectionDTO;
import com.service.onestopcollection.feignclient.BillingServiceClient;

@Service
public class BillingService {

    private final BillingServiceClient billingServiceClient;

    @Autowired
    public BillingService(BillingServiceClient billingServiceClient) {
        this.billingServiceClient = billingServiceClient;
    }

    public ResponseEntity<String> updatePaymentStatus(CollectionDTO collectionDTO) {
        return billingServiceClient.receiveCollectionDTO(collectionDTO);
    }
}