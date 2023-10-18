package com.service.onestopbilling;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.onestopbilling.dto.SubscribeDTO;
import com.service.onestopbilling.entity.Billing;
import com.service.onestopbilling.entity.Subscription;
import com.service.onestopbilling.repository.BillingRepository;
import com.service.onestopbilling.repository.SubscriptionRepository;
import com.service.onestopbilling.service.BillingService;
import com.service.onestopbilling.service.SubscriptionService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


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
    private BillingService billingService;

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
    @Sql(statements = "INSERT INTO subscription(family_id, user_id, plan_id, final_price, created_at, end_date, active, auto_renewal) VALUES (45, 123456, 789, 99.99, CURRENT_TIMESTAMP, TIMESTAMP '2023-10-31 00:00:00', 'ACTIVE', true)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
            assertEquals("plan subscribed successfully", responseBody);
            assertEquals(HttpStatus.OK, response.getStatusCode());

    }



    
}
