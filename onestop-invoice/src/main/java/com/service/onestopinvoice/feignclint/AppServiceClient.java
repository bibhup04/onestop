package com.service.onestopinvoice.feignclint;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.service.onestopinvoice.DTO.GenerateInvoiceDTO;
import com.service.onestopinvoice.DTO.InvoiceDTO;

@FeignClient(name = "ONESTOP-APP", url = "http://localhost:8080/app") 
public interface AppServiceClient {

    @PostMapping("/invoice-details")
    ResponseEntity<InvoiceDTO> generateInvoice(@RequestBody GenerateInvoiceDTO generateInvoiceDTO);
    
}
