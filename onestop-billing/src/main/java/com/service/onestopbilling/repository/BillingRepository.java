package com.service.onestopbilling.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.onestopbilling.entity.Billing;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
    
    Optional<Billing> findByUserIdAndPaymentStatus(Long userId, String paymentStatus);
    
    List<Billing> findByPaymentStatus(String paymentStatus);

    Billing findTopByUserIdOrderByCreatedAtDesc(Long userId);
}
