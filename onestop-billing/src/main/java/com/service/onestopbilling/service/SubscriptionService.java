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
        subscription.setStatus("ACTIVE");
        subscription.setAutoRenewal(true);
        return subscriptionRepository.save(subscription);
    }

    // public List<Subscription> getActiveSubscriptionsWithAutoRenewal() {
    //     return subscriptionRepository.findByStatusAndAutoRenewal("ACTIVE", true);
    // }

    public List<Subscription> getActiveSubscriptions() {
        return subscriptionRepository.findByStatus("ACTIVE");
    }

    public void renewSubscription(Date newEndDate){
        List<Subscription> subscriptions = subscriptionRepository.findByAutoRenewal(true);
        for (Subscription subscription : subscriptions) {
            subscription.setEndDate(newEndDate);
            subscriptionRepository.save(subscription);
        }
    }

    public Subscription findSubscriptionByUserId(long userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    public Subscription updateStatusToActive(Long subscriptionId) {
        Optional<Subscription> subscriptionOptional = subscriptionRepository.findById(subscriptionId);
        if (subscriptionOptional.isPresent()) {
            Subscription subscription = subscriptionOptional.get();
            if (subscription.getStatus().equals("SUSPEND")) {
                subscription.setStatus("ACTIVE");
                subscriptionRepository.save(subscription);
            } 
            return subscription;
        } else {
    
            throw new IllegalArgumentException("Subscription with id " + subscriptionId + " not found.");
        }
    }

    public void updateStatusForSubscriptions(List<Long> subscriptionIds) {
        for (Long subscriptionId : subscriptionIds) {
            Subscription subscription = subscriptionRepository.findById(subscriptionId).orElse(null);
            if (subscription != null) {
                String currentStatus = subscription.getStatus();
                if (currentStatus.equals("SUSPEND")) {
                    subscription.setStatus("TERMINATE");
                } else if (currentStatus.equals("ACTIVE")) {
                    subscription.setStatus("SUSPEND");
                }
                subscriptionRepository.save(subscription);
            }
        }
    }
}