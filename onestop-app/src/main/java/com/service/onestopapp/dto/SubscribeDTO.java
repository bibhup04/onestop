package com.service.onestopapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeDTO {
    private Long familyId;
    private long userId;
    private long planId;
    private double finalPrice;
}

