package com.service.onestopapp.dto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Tag(name = "Generate Invoice",description="The generateInvoiceMethod will recieve this dto and in response it needs InvoiceDTO containing all the details.")
public class GenerateInvoiceDTO {
    private long billId;
    private long userId;
    private long planId;
    private double finalPrice;
    
}
