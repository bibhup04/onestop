package com.service.onestopapp;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.onestopapp.dto.GenerateInvoiceDTO;
import com.service.onestopapp.dto.InvoiceDTO;
import com.service.onestopapp.dto.NewMemberDTO;
import com.service.onestopapp.dto.PlanDTO;
import com.service.onestopapp.dto.PlanIdDTO;
import com.service.onestopapp.dto.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;


import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OnestopAppIntegrationTest {

    @LocalServerPort
    private int port;

     @Autowired
    private TestRestTemplate restTemplate;

    String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsInN1YiI6ImFiYyIsImlhdCI6MTY5ODQyMTE5MCwiZXhwIjoxNjk4NDIyOTkwfQ.pDrvrRM71a6e5cm8zNgiiHnq1N87W0mMdz7Tq-gTL5E";

    private static HttpHeaders headers;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    
    /** 
     * @param path
     * @return String
     */
    private String createURLWithPort(String path) {
        return "http://localhost:" + port + "/app" + path;
    }

     @Test
    public void testGetPlans() {
        // Set up the headers
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        // Make the HTTP request
        ResponseEntity<List<PlanDTO>> response = restTemplate.exchange(
                createURLWithPort("/home"),
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<List<PlanDTO>>() {}
        );

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<PlanDTO> plans = response.getBody();
        assertNotNull(plans);

    }

    @Test
    public void testCreateFamilyDetails() {
        // Set up the headers
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        // Make the HTTP request
        ResponseEntity<UserDTO> response = restTemplate.exchange(
                createURLWithPort("/user"),
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                UserDTO.class
        );

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserDTO userDTO = response.getBody();
        assertNotNull(userDTO);
      
    }

     @Test
    public void testGetAllMember() {
        // Set up the headers
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        // Make the HTTP request
        ResponseEntity<NewMemberDTO> response = restTemplate.exchange(
                createURLWithPort("/getMember"),
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                NewMemberDTO.class
        );

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        NewMemberDTO newMemberDTO = response.getBody();
        assertNotNull(newMemberDTO);
        // Add more assertions based on your requirements
    }

     @Test
    public void testBuyPlan() {
        // Set up the headers
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        // Create a sample PlanIdDTO for testing
        PlanIdDTO planIdDTO = new PlanIdDTO();
        planIdDTO.setPlanId(5);

        // Make the HTTP request
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/plan/buy"),
                HttpMethod.POST,
                new HttpEntity<>(planIdDTO, headers),
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        
    }

    @Test
    public void testGenerateInvoice() {

        headers.setContentType(MediaType.APPLICATION_JSON);


        List<GenerateInvoiceDTO> generateInvoiceDTOs = new ArrayList<>();
        GenerateInvoiceDTO generateInvoiceDTO = new GenerateInvoiceDTO();
        generateInvoiceDTO.setBillId(1);
        generateInvoiceDTO.setFinalPrice(288);
        generateInvoiceDTO.setPlanId(4);
        generateInvoiceDTO.setUserId(8);
        generateInvoiceDTOs.add(generateInvoiceDTO);


        ResponseEntity<List<InvoiceDTO>> response = restTemplate.exchange(
                createURLWithPort("/invoice-details"),
                HttpMethod.POST,
                new HttpEntity<>(generateInvoiceDTOs, headers),
                new ParameterizedTypeReference<List<InvoiceDTO>>() {}
        );

 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<InvoiceDTO> invoiceDTOs = response.getBody();
        assertNotNull(invoiceDTOs);
  
    }
    
}
