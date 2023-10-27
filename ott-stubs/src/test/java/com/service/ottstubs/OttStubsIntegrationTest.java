package com.service.ottstubs;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.ottstubs.DTO.CollectionDTO;
import com.service.ottstubs.DTO.StubDTO;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OttStubsIntegrationTest {

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
        return "http://localhost:" + port + "/ott" + path;
    }

    @Test
    public void testReceiveStubDTO() {
        // Set up the headers
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create a sample StubDTO for testing
        StubDTO stubDTO = new StubDTO();
       

     
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/stub"),
                HttpMethod.POST,
                new HttpEntity<>(stubDTO, headers),
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("StubDTO received successfully!", response.getBody());
        
    }

     @Test
    public void testReceiveCollectionDTO() {
       
        headers.setContentType(MediaType.APPLICATION_JSON);

        CollectionDTO collectionDTO = new CollectionDTO();


        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/collection"),
                HttpMethod.POST,
                new HttpEntity<>(collectionDTO, headers),
                String.class
        );


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Amount collected", response.getBody());

    }

    
}
