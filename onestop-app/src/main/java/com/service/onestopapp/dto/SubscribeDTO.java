package com.service.onestopapp.dto;


import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
public class SubscribeDTO {
    private Long familyId;
    private long userId;
    private long planId;
    private double finalPrice;
}

