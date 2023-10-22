package com.service.onestopinvoice.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long planId;

    private String planDescription;

    private Double finalPrice;

    private String emailId;

    private Long userId;

    private String userName;

    private Long billId;

    private String path;

    @CreationTimestamp
    private Date createdAt;
}