package com.service.onestopinvoice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.service.onestopinvoice.DTO.GenerateInvoiceDTO;
import com.service.onestopinvoice.DTO.InvoiceDTO;
import com.service.onestopinvoice.feignclint.AppServiceClient;

@Service
public class AppService {

    private final AppServiceClient appServiceClient;

    @Autowired
    public AppService(AppServiceClient appServiceClient) {
        this.appServiceClient = appServiceClient;
    }

   

    
    /** 
     * @param generateInvoiceDTOs
     * @return List<InvoiceDTO>
     */
    public List<InvoiceDTO> getInvoiceDetails(List<GenerateInvoiceDTO> generateInvoiceDTOs) {
        ResponseEntity<List<InvoiceDTO>> responseEntity = appServiceClient.generateInvoice(generateInvoiceDTOs);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            
            return null;
        }
    }
}
