package com.service.onestopbilling;

import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.lang.reflect.Field;

import com.service.onestopbilling.controller.SubscribeController;
import com.service.onestopbilling.dto.SubscribeDTO;
import com.service.onestopbilling.dto.UserDTO;
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
            assertEquals(mockSubscription.getStatus(), responseSubscription.getStatus());
            assertEquals(mockSubscription.isAutoRenewal(), responseSubscription.isAutoRenewal());
        }
    }


    @Test
    public void testUserDTO() {
     
        Long id = 1L;
        String name = "John Doe";
        String email = "johndoe@example.com";
        String phoneNo = "1234567890";
        String password = "password123";
        String role = "user";
        String status = "active";
     
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setName(name);
        userDTO.setEmail(email);
        userDTO.setPhoneNo(phoneNo);
        userDTO.setPassword(password);
        userDTO.setRole(role);
        userDTO.setStatus(status);

        assertEquals(id, userDTO.getId());
        assertEquals(name, userDTO.getName());
        assertEquals(email, userDTO.getEmail());
        assertEquals(phoneNo, userDTO.getPhoneNo());
        assertEquals(password, userDTO.getPassword());
        assertEquals(role, userDTO.getRole());
        assertEquals(status, userDTO.getStatus());
    }
    

}
