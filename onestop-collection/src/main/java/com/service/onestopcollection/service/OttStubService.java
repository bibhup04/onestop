package com.service.onestopcollection.service;

import org.springframework.stereotype.Service;

import com.service.onestopcollection.DTO.CollectionDTO;
import com.service.onestopcollection.feignclient.OttStubServiceClient;

@Service
public class OttStubService {

    private final OttStubServiceClient ottStubServiceClient;

    public OttStubService(OttStubServiceClient ottStubServiceClient) {
        this.ottStubServiceClient = ottStubServiceClient;
    }

    
    /** 
     * @param collectionDTO
     * @return String
     */
    public String sendCollectionDTO(CollectionDTO collectionDTO) {
        

        return ottStubServiceClient.receiveCollectionDTO(collectionDTO);
    }
}
