package com.service.onestopapp.feignclint;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.service.onestopapp.dto.StubDTO;

@FeignClient(name = "OTT-STUB", url = "http://localhost:8080/ott") 
public interface OttStubServiceClient {

    @PostMapping("/stub")
    String receiveStubDTO(@RequestBody StubDTO stubDTO);
    
}
