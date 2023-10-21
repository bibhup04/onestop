package com.service.onestopbilling.feignclint;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.service.onestopbilling.dto.GenerateInvoiceDTO;

@FeignClient(name = "ONESTOP-INVOICE", url = "http://localhost:8085/invoice") 
public interface InvoiceServiceClient {

    @PostMapping("/generate-invoice")
    ResponseEntity<String> generateInvoice(@RequestBody GenerateInvoiceDTO generateInvoiceDTO);
}
