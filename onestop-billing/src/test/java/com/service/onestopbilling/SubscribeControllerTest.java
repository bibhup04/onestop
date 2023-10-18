package com.service.onestopbilling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.service.onestopbilling.controller.SubscribeController;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.onestopbilling.controller.SubscribeController;
import com.service.onestopbilling.dto.SubscribeDTO;
import com.service.onestopbilling.entity.Subscription;
import com.service.onestopbilling.repository.SubscriptionRepository;
import com.service.onestopbilling.service.SubscriptionService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SubscribeControllerTest {

    @Test
    void contextLoads() {
    }

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SubscriptionService subscriptionService;

    @MockBean
    private SubscriptionRepository subscriptionRepository;

    @Test
    void allcontacts() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/subscribe/all").accept(MediaType.APPLICATION_JSON)).andReturn();
        System.out.println(mvcResult.getResponse());

    }

    @Test
    public void testSubscribePlan() {
        SubscribeDTO subscribeDTO = new SubscribeDTO();
        subscribeDTO.setFamilyId(1L);
        subscribeDTO.setPlanId(2);
        subscribeDTO.setUserId(6);
        subscribeDTO.setFinalPrice(120);

        SubscribeController subscribeController = new SubscribeController(subscriptionService);

        Subscription mockSubscription = new Subscription();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date;
        try {
            date = formatter.parse("17-10-2023"); // Change this to your desired date
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        
        when(subscriptionService.saveSubscription(eq(date), eq(subscribeDTO)))
            .thenReturn(mockSubscription);
    
        ResponseEntity<String> response = subscribeController.subscribePlan(subscribeDTO);
    
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("plan subscribed successfully", response.getBody());
    }


}


 