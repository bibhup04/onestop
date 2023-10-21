package com.service.onestopinvoice.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceDTO {
    private Long planId;
    private String planDescription;
    private Double price;
    private List<Ott> otts;
    private String emailId;
    private List<NameAndPhone> nameAndPhones;
}

