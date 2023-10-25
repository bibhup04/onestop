package com.service.onestopapp.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Subscription {

    private Long subscriptionId;
    private Long familyId;
    private long userId; 
    private long planId; 
    private Double finalPrice;
    private Date createdAt;
    private Date endDate;
    private String status;
    private boolean autoRenewal;
}
