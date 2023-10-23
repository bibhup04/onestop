package com.service.onestopbilling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.onestopbilling.entity.Billing;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
    
    Billing findByUserIdAndPaymentStatus(Long userId, String paymentStatus);
}
