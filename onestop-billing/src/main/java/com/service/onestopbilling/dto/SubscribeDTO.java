package com.service.onestopbilling.dto;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeDTO {
    private Long familyId;
    private long userId;
    private long planId;
    private double finalPrice;
}

