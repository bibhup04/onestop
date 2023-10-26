package com.service.onestopcollection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.service.onestopcollection.DTO.CollectionDTO;
import com.service.onestopcollection.DTO.UserDTO;
import com.service.onestopcollection.entity.Collection;

public class OneStopCollectionUnitTests {

     @Test
    public void testCollectionEntity() {
        Long expectedCollectionId = 1L;
        Long expectedSubscriptionId = 123L;
        Long expectedUserId = 456L;
        Double expectedAmountCollected = 100.50;
        Long expectedBillId = 789L;

        Collection collection = new Collection();
        collection.setCollectionId(expectedCollectionId);
        collection.setSubscriptionId(expectedSubscriptionId);
        collection.setUserId(expectedUserId);
        collection.setAmountCollected(expectedAmountCollected);
        collection.setBillId(expectedBillId);

        assertNotNull(collection);
        assertEquals(expectedCollectionId, collection.getCollectionId());
        assertEquals(expectedSubscriptionId, collection.getSubscriptionId());
        assertEquals(expectedUserId, collection.getUserId());
        assertEquals(expectedAmountCollected, collection.getAmountCollected());
        assertEquals(expectedBillId, collection.getBillId());
    }

     @Test
    public void testCollectionDTO() {
        Long expectedSubscriptionId = 1L;
        Long expectedUserId = 2L;
        Double expectedAmountCollected = 100.50;
        Long expectedBillId = 3L;

        CollectionDTO collectionDTO = new CollectionDTO();
        collectionDTO.setSubscriptionId(expectedSubscriptionId);
        collectionDTO.setUserId(expectedUserId);
        collectionDTO.setAmountCollected(expectedAmountCollected);
        collectionDTO.setBillId(expectedBillId);

        assertEquals(expectedSubscriptionId, collectionDTO.getSubscriptionId());
        assertEquals(expectedUserId, collectionDTO.getUserId());
        assertEquals(expectedAmountCollected, collectionDTO.getAmountCollected());
        assertEquals(expectedBillId, collectionDTO.getBillId());
    }

      @Test
    public void testUserDTO() {
        Long expectedId = 1L;
        String expectedName = "John Doe";
        String expectedEmail = "johndoe@example.com";
        String expectedPhoneNo = "1234567890";
        String expectedPassword = "secretpassword";
        String expectedRole = "user";
        String expectedStatus = "active";

        UserDTO userDTO = new UserDTO();
        userDTO.setId(expectedId);
        userDTO.setName(expectedName);
        userDTO.setEmail(expectedEmail);
        userDTO.setPhoneNo(expectedPhoneNo);
        userDTO.setPassword(expectedPassword);
        userDTO.setRole(expectedRole);
        userDTO.setStatus(expectedStatus);

        assertEquals(expectedId, userDTO.getId());
        assertEquals(expectedName, userDTO.getName());
        assertEquals(expectedEmail, userDTO.getEmail());
        assertEquals(expectedPhoneNo, userDTO.getPhoneNo());
        assertEquals(expectedPassword, userDTO.getPassword());
        assertEquals(expectedRole, userDTO.getRole());
        assertEquals(expectedStatus, userDTO.getStatus());
    }
    
}
