package com.service.onestopbilling.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.onestopbilling.entity.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    
    List<Subscription> findByStatusAndAutoRenewal(String active, boolean autoRenewal);

    List<Subscription> findByAutoRenewal(boolean autoRenewal);

    List<Subscription> findByStatus(String active);

    Subscription findByUserId(long userId);
    
}