package com.service.ottstubs.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
