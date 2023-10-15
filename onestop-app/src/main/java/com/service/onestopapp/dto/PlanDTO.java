package com.service.onestopapp.dto;

import java.util.List;

import com.service.onestopapp.entity.Ott;

import lombok.Data;

@Data
public class PlanDTO {
    private Long planId;
    private Long billingCycle;
    private Integer memberCount;
    private String planDescription;
    private Integer ottCount;
    private Long streams;
    private Double price;
    private Double discount;
    private Double finalPrice;
    private List<Ott> otts;
}
