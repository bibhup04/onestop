package com.service.onestopbilling.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.onestopbilling.entity.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    
    List<Subscription> findByActiveAndAutoRenewal(String active, boolean autoRenewal);

    List<Subscription> findByAutoRenewal(boolean autoRenewal);

    List<Subscription> findByActive(String active);

    Subscription findByUserId(long userId);
    
}