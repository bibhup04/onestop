package com.service.onestopcollection.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.onestopcollection.DTO.CollectionDTO;
import com.service.onestopcollection.entity.Collection;
import com.service.onestopcollection.repository.CollectionRepository;

@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;

    @Autowired
    public CollectionService(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    // Method to save a collection entry
    public Collection saveCollection(CollectionDTO collectionDTO) {
        Collection collection = new Collection();
        collection.setBillId(collectionDTO.getBillId());
        collection.setAmountCollected(collectionDTO.getAmountCollected());
        collection.setSubscriptionId(collectionDTO.getSubscriptionId());
        collection.setUserId(collectionDTO.getUserId());
         
        return collectionRepository.save(collection);
    }

   
    public Optional<Collection> getCollectionById(Long id) {
        return collectionRepository.findById(id);
    }

  
    public List<Collection> getAllCollections() {
        return collectionRepository.findAll();
    }

}