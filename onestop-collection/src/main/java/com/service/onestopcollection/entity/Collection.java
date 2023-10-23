package com.service.onestopcollection.entity;

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
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collectionId;

    private Long subscriptionId;

    private Long userId;

    private Double amountCollected;

    private Long billId;

    @CreationTimestamp
    private Date collectedAt;

}

