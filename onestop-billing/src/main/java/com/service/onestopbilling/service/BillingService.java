package com.service.onestopbilling.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.onestopbilling.entity.Billing;
import com.service.onestopbilling.entity.Subscription;
import com.service.onestopbilling.repository.BillingRepository;

@Service
public class BillingService {

    private final BillingRepository billingRepository;
    private final SubscriptionService subscriptionService;

    @Autowired
    public BillingService(BillingRepository billingRepository, SubscriptionService subscriptionService) {
        this.billingRepository = billingRepository;
        this.subscriptionService = subscriptionService;
    }

    public List<Billing> getAllBillings() {
        return billingRepository.findAll();
    }

    public Optional<Billing> getBillingById(Long id) {
        return billingRepository.findById(id);
    }

    public Billing saveBilling(Billing billing) {
        return billingRepository.save(billing);
    }

    public void deleteBilling(Long id) {
        billingRepository.deleteById(id);
    }

    public void createbills(){
        List<Subscription> subscriptions = subscriptionService.getActiveSubscriptions();
        for(Subscription sub : subscriptions){
            Billing billing = new Billing();
            billing.setAmount(450);
            billing.setSubscriptionId(sub.getSubscriptionId());
            billing.setPaymentStatus("Pending");
            billing.setUserId(sub.getUserId());
            billingRepository.save(billing);
        }
    }
}