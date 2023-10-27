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

    
    /** 
     * @return List<Subscription>
     */
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public boolean isSubscriptionPresentForUser(long userId) {
        Optional<Subscription> existingSubscription = subscriptionRepository.findByUserId(userId);
        return existingSubscription.isPresent();
    }

    public void deleteSubscriptionById(Long subscriptionId) {
        if (subscriptionRepository.existsById(subscriptionId)) {
            subscriptionRepository.deleteById(subscriptionId);
        } else {
            throw new IllegalArgumentException("Subscription with ID " + subscriptionId + " does not exist.");
        }
    }

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
        Optional<Subscription> subscriptionOptional = subscriptionRepository.findByUserId(userId);
        return subscriptionOptional.orElse(null);
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
            return null;   
           // throw new IllegalArgumentException("Subscription with id " + subscriptionId + " not found.");
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