package com.service.onestopcollection;


import com.service.onestopcollection.DTO.CollectionDTO;
import com.service.onestopcollection.entity.Collection;
import com.service.onestopcollection.feignclient.BillingServiceClient;
import com.service.onestopcollection.repository.CollectionRepository;
import com.service.onestopcollection.service.CollectionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CollectionServiceTest {


    @Mock
    private BillingServiceClient billingServiceClient;

    @InjectMocks
    private CollectionService collectionService;

    @Test
    public void testSaveCollection() {
        // Mocking the repository
        CollectionRepository collectionRepository = Mockito.mock(CollectionRepository.class);

        // Creating an instance of the service
        CollectionService collectionService = new CollectionService(collectionRepository);

        // Creating a sample CollectionDTO
        CollectionDTO collectionDTO = new CollectionDTO();
        collectionDTO.setSubscriptionId(1L);
        collectionDTO.setUserId(1L);
        collectionDTO.setAmountCollected(100.0);
        collectionDTO.setBillId(1L);

        // Creating an expected Collection object
        Collection expectedCollection = new Collection();
        expectedCollection.setSubscriptionId(1L);
        expectedCollection.setUserId(1L);
        expectedCollection.setAmountCollected(100.0);
        expectedCollection.setBillId(1L);

        // Mocking the save method of the repository
        Mockito.when(collectionRepository.save(Mockito.any(Collection.class))).thenReturn(expectedCollection);

        // Testing the service method
        Collection savedCollection = collectionService.saveCollection(collectionDTO);

        // Assertions
        assertEquals(expectedCollection.getSubscriptionId(), savedCollection.getSubscriptionId());
        assertEquals(expectedCollection.getUserId(), savedCollection.getUserId());
        assertEquals(expectedCollection.getAmountCollected(), savedCollection.getAmountCollected());
        assertEquals(expectedCollection.getBillId(), savedCollection.getBillId());
    }

     @Test
    public void testUpdatePaymentStatus() {
        // Prepare the test data
        CollectionDTO collectionDTO = new CollectionDTO();
        collectionDTO.setSubscriptionId(1L);
        collectionDTO.setUserId(123L);
        collectionDTO.setAmountCollected(100.0);
        collectionDTO.setBillId(456L);

        // Mock the behavior of the billingServiceClient
        ResponseEntity<String> mockedResponseEntity = ResponseEntity.ok("Mocked response");
        when(billingServiceClient.receiveCollectionDTO(collectionDTO)).thenReturn(mockedResponseEntity);

        // Call the method under test
        //ResponseEntity<String> response = collectionService.updatePaymentStatus(collectionDTO); 

        // Assertions
       // assertEquals(mockedResponseEntity, response);
    }
}
