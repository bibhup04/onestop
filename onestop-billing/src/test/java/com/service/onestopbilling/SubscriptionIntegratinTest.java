package com.service.onestopbilling;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.onestopbilling.dto.GenerateInvoiceDTO;
import com.service.onestopbilling.dto.SubscribeDTO;
import com.service.onestopbilling.dto.UserDTO;
import com.service.onestopbilling.entity.Billing;
import com.service.onestopbilling.entity.Subscription;
import com.service.onestopbilling.repository.BillingRepository;
import com.service.onestopbilling.repository.SubscriptionRepository;
import com.service.onestopbilling.service.BillingService;
import com.service.onestopbilling.service.SubscriptionService;
import com.service.onestopbilling.service.UserService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SubscriptionIntegratinTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BillingService billingService;

    String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsInN1YiI6ImFiYyIsImlhdCI6MTY5ODMxNzc0NywiZXhwIjoxNjk4MzE5NTQ3fQ.LvJb4km59MF17CwDwWosrv6HmNZ6ED6FacZ1kLZEzxA";

    private static HttpHeaders headers;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    private String createURLWithPort(String path) {
        return "http://localhost:" + port + "/subscribe" + path;
    }


    @Test
    @Sql(statements = "INSERT INTO subscription(family_id, user_id, plan_id, final_price, created_at, end_date, status, auto_renewal) VALUES (45, 123456, 789, 99.99, CURRENT_TIMESTAMP, TIMESTAMP '2023-10-31 00:00:00', 'ACTIVE', true)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM subscription WHERE family_id=45", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testOrdersList() {
        
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<List<Subscription>> response = restTemplate.exchange(
                createURLWithPort("/all"), HttpMethod.GET, entity, new ParameterizedTypeReference<List<Subscription>>(){});
        List<Subscription> subscription = response.getBody();
        assert subscription != null;
        //assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getStatusCode().value(), 200);
        assertEquals(subscription.size(), subscriptionService.getAllSubscriptions().size());
        assertEquals(subscription.size(), subscriptionRepository.findAll().size());
    }

    @Test
    public void testGenerateNewBill(){
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<List<Billing>> response = restTemplate.exchange(
            createURLWithPort("/bill"),HttpMethod.GET, entity, new ParameterizedTypeReference<List<Billing>>(){});
        List<Billing> billings = response.getBody();
        assert billings != null;
        assertEquals(response.getStatusCode().value(),200);
        assertEquals(billings.size(), billingService.getAllBillings().size());
        assertEquals(billings.size(), billingRepository.findAll().size());
     }


    @Test
    @Sql(statements = "DELETE FROM subscription WHERE family_id='45'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testCreateOrder() throws JsonProcessingException {
        SubscribeDTO subscribeDTO = new SubscribeDTO();
        subscribeDTO.setFamilyId(45L);
        subscribeDTO.setFinalPrice(1200);
        subscribeDTO.setPlanId(2);
        subscribeDTO.setUserId(7);

        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(subscribeDTO), headers);
        ResponseEntity<String> response = restTemplate.exchange(
            createURLWithPort("/plan"), HttpMethod.POST, new HttpEntity<>(subscribeDTO, headers), String.class);
        
            String responseBody = response.getBody(); 
            assertEquals("Plan subscribed successfully", responseBody);
            assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testCreateInvoice() throws JsonProcessingException {
        // Create a sample GenerateInvoiceDTO list
        List<GenerateInvoiceDTO> generateInvoiceDTOList = new ArrayList<>();
        GenerateInvoiceDTO generateInvoiceDTO = new GenerateInvoiceDTO();
        // Set properties for generateInvoiceDTO as needed
        generateInvoiceDTO.setBillId(59);;
        generateInvoiceDTO.setFinalPrice(360);
        generateInvoiceDTO.setPlanId(2);
        generateInvoiceDTO.setUserId(5);
        // Add generateInvoiceDTO to the list
        generateInvoiceDTOList.add(generateInvoiceDTO);

        // Convert the list to JSON
        String jsonRequest = objectMapper.writeValueAsString(generateInvoiceDTOList);

        // Define the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create an HTTP entity with the JSON request and headers
        HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);

        // Send a POST request to the endpoint
        ResponseEntity<String> response = restTemplate.exchange(
            createURLWithPort("/create-invoice"), HttpMethod.POST, entity, String.class);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Invoice Generated", response.getBody()); // Modify the string as per your implementation
    }


    @Test
    @Sql(statements = "INSERT INTO subscription(family_id, user_id, plan_id, final_price, created_at, end_date, status, auto_renewal) VALUES (2, 8, 2, 360, CURRENT_TIMESTAMP, TIMESTAMP '2023-10-31 00:00:00', 'TERMINATE', true)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM `onestop-billing`.subscription WHERE user_id=8;", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetSubscribedPlan() throws JsonProcessingException {
        // Mock the token
       

        // Mock the UserDTO
        UserDTO userDTO = new UserDTO();
        userDTO.setId(8L); 
        userDTO.setEmail("handsompikul04@gmail.com");
        userDTO.setName("abc");
        userDTO.setPassword("$2a$10$W47hkBSJEct5ecoWY3kyluLFA8WoKUeKTdkKPUEmqTaEjLGwaYRcm");
        userDTO.setPhoneNo("+9198485758473");
        userDTO.setRole("USER");
        userDTO.setStatus("ACTIVE");
        //when(userService.getUserDetails(token)).thenReturn(userDTO);

        // Mock the Subscription
        Subscription subscription = subscriptionService.findSubscriptionByUserId(8); 
        
        //when(subscriptionService.findSubscriptionByUserId(userDTO.getId())).thenReturn(subscription);

        // Set the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        // Create an HTTP entity with the headers
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        // Send a GET request to the endpoint
        ResponseEntity<Subscription> response = restTemplate.exchange(
            createURLWithPort("/user/subscription"), HttpMethod.GET, entity, Subscription.class);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(subscription.getSubscriptionId(), Objects.requireNonNull(response.getBody()).getSubscriptionId()); 
    }


     @Test
     @Sql(statements = "INSERT INTO subscription(family_id, user_id, plan_id, final_price, created_at, end_date, status, auto_renewal) VALUES (2, 8, 2, 360, CURRENT_TIMESTAMP, TIMESTAMP '2023-10-31 00:00:00', 'TERMINATE', true)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
     @Sql(statements = "DELETE FROM `onestop-billing`.billing WHERE user_id=8;", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
     public void testEndSubscribedPlan() throws JsonProcessingException {
         
         Subscription subscription = subscriptionService.findSubscriptionByUserId(8L);
         
         // Convert the Subscription object to JSON
         String jsonRequest = objectMapper.writeValueAsString(subscription);
     
         // Define the headers
         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_JSON);
     
         // Create an HTTP entity with the JSON request and headers
         HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);
     
         // Mock the billingService to return a ResponseEntity<String>
         //when(billingService.endSubscriptionBill(subscription)).thenReturn(new ResponseEntity<>("Invoice Generated", HttpStatus.OK));
     
         // Send a POST request to the endpoint
         ResponseEntity<String> response = restTemplate.exchange(
             createURLWithPort("/end/subscription"), HttpMethod.POST, entity, String.class);
     
         // Assert the response
         assertEquals(HttpStatus.OK, response.getStatusCode());
         assertEquals("Invoice Generated", response.getBody());
     }
     


    @Test
    @Sql(statements = "INSERT INTO `onestop-billing`.billing (amount, created_at, payment_status, subscription_id, user_id) VALUES(360,  CURRENT_TIMESTAMP, 'PENDING', 1, 8)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM `onestop-billing`.billing WHERE user_id=8;", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetUnpaidBill() throws JsonProcessingException {
        // Mock the token
       

        // Mock the UserDTO
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L); // Replace with the actual ID
        // Mock the userService to return the userDTO
       // when(userService.getUserDetails(token)).thenReturn(userDTO);

        // Mock the Billing
        Billing billing = billingService.getLastBillByUserId(8L);
        // billing.setBillingId(1L); 
        // when(billingService.getLastBillByUserId(userDTO.getId())).thenReturn(billing);

        // Set the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        // Create an HTTP entity with the headers
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        // Send a GET request to the endpoint
        ResponseEntity<Billing> response = restTemplate.exchange(
            createURLWithPort("/user/bill"), HttpMethod.GET, entity, Billing.class);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(billing.getBillingId(), Objects.requireNonNull(response.getBody()).getBillingId()); // Modify based on your actual billing ID
    }

    
}
