package com.service.onestopbilling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.math.stat.descriptive.summary.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.lang.reflect.Field;

import com.service.onestopbilling.controller.SubscribeController;
import com.service.onestopbilling.dto.SubscribeDTO;
import com.service.onestopbilling.entity.Billing;
import com.service.onestopbilling.entity.CustomDateHandler;
import com.service.onestopbilling.entity.Subscription;
import com.service.onestopbilling.repository.BillingRepository;
import com.service.onestopbilling.service.BillingService;
import com.service.onestopbilling.service.SubscriptionService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OnestopBillingApplicationTests {

	@LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

	@Mock
    private SubscriptionService subscriptionService;

	@Mock
	private CustomDateHandler customDateHandler;

    @Mock
    private BillingService billingService;

    @Mock
    private BillingRepository billingRepository;

    @InjectMocks
    private SubscribeController subscribeController;

	private Date endDate;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/subscribe");
    }


	@Test
    public void testAddProduct() {
        SubscribeDTO  subscribeDTO = new SubscribeDTO();
        subscribeDTO.setFamilyId(1L);
        subscribeDTO.setUserId(2);
        subscribeDTO.setPlanId(3);
        subscribeDTO.setFinalPrice(99);

		Subscription mockSubscription = new Subscription();
		mockSubscription.setSubscriptionId(1L);
		mockSubscription.setFamilyId(1L);
		mockSubscription.setUserId(123456L);
		mockSubscription.setPlanId(789L);
		mockSubscription.setFinalPrice(99.99);
		mockSubscription.setEndDate(customDateHandler.getEndDate());
		mockSubscription.setActive("ACTIVE");
		mockSubscription.setAutoRenewal(true);

		try {
			Field createdAtField = Subscription.class.getDeclaredField("createdAt");
			createdAtField.setAccessible(true);
			createdAtField.set(mockSubscription, new Date());
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace(); 
		}

        ResponseEntity<String> expectedResponse = new ResponseEntity<>("plan subscribed successfully", HttpStatus.OK);

        when(subscriptionService.saveSubscription(endDate, subscribeDTO)).thenReturn(mockSubscription);

        ResponseEntity<String> actualResponse = subscribeController.subscribePlan(subscribeDTO);


        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
    }


	@Test
    public void testGetAllSubscriptions() {
        // Mock list of subscriptions
        List<Subscription> mockSubscriptions = new ArrayList<>();
        mockSubscriptions.add(new Subscription(1L, 1L, 123456L, 789L, 99.99, new Date(), new Date(), "ACTIVE", true));
        mockSubscriptions.add(new Subscription(2L, 2L, 789123L, 456L, 199.99, new Date(), new Date(), "ACTIVE", false));

        when(subscriptionService.getAllSubscriptions()).thenReturn(mockSubscriptions);

        ResponseEntity<List<Subscription>> response = subscribeController.getAllSubscriptions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size()); 

		for (int i = 0; i < mockSubscriptions.size(); i++) {
            Subscription mockSubscription = mockSubscriptions.get(i);
            Subscription responseSubscription = response.getBody().get(i);

            assertEquals(mockSubscription.getSubscriptionId(), responseSubscription.getSubscriptionId());
            assertEquals(mockSubscription.getFamilyId(), responseSubscription.getFamilyId());
            assertEquals(mockSubscription.getUserId(), responseSubscription.getUserId());
            assertEquals(mockSubscription.getPlanId(), responseSubscription.getPlanId());
            assertEquals(mockSubscription.getFinalPrice(), responseSubscription.getFinalPrice(), 0.001);
            assertEquals(mockSubscription.getCreatedAt(), responseSubscription.getCreatedAt());
            assertEquals(mockSubscription.getEndDate(), responseSubscription.getEndDate());
            assertEquals(mockSubscription.getActive(), responseSubscription.getActive());
            assertEquals(mockSubscription.isAutoRenewal(), responseSubscription.isAutoRenewal());
        }
    }
    

	// @Test
    // public void testCreateBills() {
    //     // Set up mock data for testing
    //     List<Subscription> mockSubscriptions = new ArrayList<>();
    //      mockSubscriptions.add(new Subscription(1L, 1L, 123456L, 789L, 99.99, new Date(), new Date(), "ACTIVE", true));
    //     mockSubscriptions.add(new Subscription(2L, 2L, 789123L, 456L, 199.99, new Date(), new Date(), "ACTIVE", false));

    //     // ... (add some mock Subscription objects to the list)

    //     when(subscriptionService.getActiveSubscriptions()).thenReturn(mockSubscriptions);

    //     // Call the method to be tested
    //     billingService.createbills();

    //     // Verify that the billingRepository method is called with the correct Billing object
    //     for (Subscription sub : mockSubscriptions) {
    //         verify(billingRepository).save(Mockito.any(Billing.class));
    //     }
    // }

}
