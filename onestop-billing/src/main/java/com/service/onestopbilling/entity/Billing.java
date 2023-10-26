package com.service.onestopbilling.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


import lombok.Getter;

import lombok.Setter;

@Entity
@Getter
@Setter
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billingId;

    private Long subscriptionId;

    private Long userId;

    @CreationTimestamp
    private Date createdAt;

    private double amount;

    private String paymentStatus;
    
}