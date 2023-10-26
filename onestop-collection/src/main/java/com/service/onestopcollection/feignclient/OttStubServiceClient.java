package com.service.onestopcollection.feignclient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.service.onestopcollection.DTO.CollectionDTO;

@FeignClient(name = "OTT-STUB", url = "http://localhost:8080/ott") 
public interface OttStubServiceClient {

    @PostMapping("/collection")
    String receiveCollectionDTO(@RequestBody CollectionDTO collectionDTO);
    
}