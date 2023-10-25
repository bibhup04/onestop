package com.service.onestopapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.service.onestopapp.dto.SubscribeDTO;
import com.service.onestopapp.dto.Subscription;
import com.service.onestopapp.dto.UserDTO;
import com.service.onestopapp.entity.Family;
import com.service.onestopapp.feignclint.BillingServiceClient;

@Service
public class SubscribePlanService {

    private final BillingServiceClient billingServiceClient;
    private final PlanService planService;
    private final OttStubService ottStubService;

    @Autowired
    public SubscribePlanService(OttStubService ottStubService,BillingServiceClient billingServiceClient, PlanService planService) {
        this.billingServiceClient = billingServiceClient;
        this.planService = planService;
        this.ottStubService = ottStubService;
    }

    public ResponseEntity<String> subscribePlan(long planId, UserDTO userDTO, Family family) {
        SubscribeDTO subscribeDTO = new SubscribeDTO(); 

        subscribeDTO.setPlanId(planId);
        subscribeDTO.setUserId(userDTO.getId());
        subscribeDTO.setFamilyId(family.getFamilyId());
        subscribeDTO.setFinalPrice(planService.getFinalPriceByPlanId(planId));

        ottStubService.sendStubDTO(planService.getPlansById(planId).get(), userDTO,family);

        return billingServiceClient.subscribePlan(subscribeDTO);
    }

    public ResponseEntity<Subscription> getSubscriptionDetails(String token){
        return billingServiceClient.getSubscribedPlan(token);
    }

   
    
}
