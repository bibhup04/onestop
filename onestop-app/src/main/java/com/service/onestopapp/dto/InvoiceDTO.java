package com.service.onestopapp.dto;

import java.util.List;

import com.service.onestopapp.entity.Ott;

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
