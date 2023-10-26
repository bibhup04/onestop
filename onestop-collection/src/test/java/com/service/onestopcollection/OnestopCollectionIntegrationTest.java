package com.service.onestopcollection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.onestopcollection.DTO.CollectionDTO;
import com.service.onestopcollection.DTO.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OnestopCollectionIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

     String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsInN1YiI6ImFiYyIsImlhdCI6MTY5ODM1MDk1MiwiZXhwIjoxNjk4MzUyNzUyfQ.l7XL-sJ7JY5IefX8WIVDkhcEeC_Ekxs_qeDpRy_icq8";

    private static HttpHeaders headers;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    private String createURLWithPort(String path) {
        return "http://localhost:" + port + "/collection" + path;
    }

    @Test
    public void testCreateFamilyDetails() {
        // Set up the HTTP headers
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        // Make a GET request to the endpoint
        ResponseEntity<UserDTO> response = restTemplate.exchange(
                createURLWithPort("/user"), HttpMethod.GET, entity, UserDTO.class);

        // Check the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserDTO userDTO = response.getBody();
        assertNotNull(userDTO);
        assertEquals("abc", userDTO.getName());
    }


    
}
