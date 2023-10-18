package com.service.onestopbilling.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.onestopbilling.dto.SubscribeDTO;
import com.service.onestopbilling.entity.Subscription;
import com.service.onestopbilling.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    // public Optional<Subscription> getSubscriptionById(Long id) {
    //     return subscriptionRepository.findById(id);
    // }

    public Subscription saveSubscription(Date endDate, SubscribeDTO subscriptionDto) {
        Subscription subscription = new Subscription();
        subscription.setUserId(subscriptionDto.getUserId());
        subscription.setFamilyId(subscriptionDto.getFamilyId());
        subscription.setPlanId(subscriptionDto.getPlanId());
        subscription.setFinalPrice(subscriptionDto.getFinalPrice());
        subscription.setEndDate(endDate);
        subscription.setActive("ACTIVE");
        subscription.setAutoRenewal(true);
        return subscriptionRepository.save(subscription);
    }

    // public List<Subscription> getActiveSubscriptionsWithAutoRenewal() {
    //     return subscriptionRepository.findByActiveAndAutoRenewal("ACTIVE", true);
    // }

    public List<Subscription> getActiveSubscriptions() {
        return subscriptionRepository.findByActive("ACTIVE");
    }

    public void renewSubscription(Date newEndDate){
        List<Subscription> subscriptions = subscriptionRepository.findByAutoRenewal(true);
        for (Subscription subscription : subscriptions) {
            subscription.setEndDate(newEndDate);
            subscriptionRepository.save(subscription);
        }

    }
}