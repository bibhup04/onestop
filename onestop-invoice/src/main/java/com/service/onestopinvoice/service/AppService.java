package com.service.onestopinvoice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.service.onestopinvoice.DTO.GenerateInvoiceDTO;
import com.service.onestopinvoice.DTO.InvoiceDTO;
import com.service.onestopinvoice.feignclint.AppServiceClient;

@Service
public class AppService {

    @Autowired
    private AppServiceClient appServiceClient;

    public InvoiceDTO getInvoiceDetails(GenerateInvoiceDTO generateInvoiceDTO) {
        ResponseEntity<InvoiceDTO> responseEntity = appServiceClient.generateInvoice(generateInvoiceDTO);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            // Handle the case when the status code is not OK
            return null;
        }
    }
}
