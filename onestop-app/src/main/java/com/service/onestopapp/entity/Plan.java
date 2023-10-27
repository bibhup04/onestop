package com.service.onestopapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    private Long billingCycle;

    private Integer memberCount;

    private String planDescription;

    private Integer ottCount;

    private Long streams;

    private Double price;

    private Double discount;

    private Double finalPrice;
}
