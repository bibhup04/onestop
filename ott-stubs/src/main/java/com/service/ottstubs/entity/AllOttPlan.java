package com.service.ottstubs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class AllOttPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long allOttPlansId;

    private String ottName;

    private String planTitle;

    private Double planPrice;

    private Long planDuration; // Updated the data type to Long
}
