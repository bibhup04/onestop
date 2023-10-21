package com.service.onestopbilling.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateInvoiceDTO {
    private long billId;
    private long userId;
    private long planId;
    private double finalPrice;
    
}
