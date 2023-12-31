package com.service.onestopbilling.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.service.onestopbilling.dto.GenerateInvoiceDTO;
import com.service.onestopbilling.feignclient.InvoiceServiceClient;

@Service
public class InvoiceService {

    private final InvoiceServiceClient invoiceServiceClient;

    @Autowired
    public InvoiceService(InvoiceServiceClient invoiceServiceClient) {
        this.invoiceServiceClient = invoiceServiceClient;
    }

    
    /** 
     * @param generateInvoiceDTOList
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> createInvoice(List<GenerateInvoiceDTO> generateInvoiceDTOList) {
        return invoiceServiceClient.generateInvoice(generateInvoiceDTOList);
    }
    
}
