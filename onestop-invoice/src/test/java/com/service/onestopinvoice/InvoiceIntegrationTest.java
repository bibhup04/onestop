package com.service.onestopinvoice;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.onestopinvoice.DTO.GenerateInvoiceDTO;
import com.service.onestopinvoice.DTO.InvoiceDTO;
import com.service.onestopinvoice.DTO.InvoiceIdDTO;
import com.service.onestopinvoice.DTO.NameAndPhone;
import com.service.onestopinvoice.DTO.Ott;
import com.service.onestopinvoice.entity.Invoice;
import com.service.onestopinvoice.service.InvoiceService;
import com.service.onestopinvoice.service.PdfService;
import com.service.onestopinvoice.service.UserService;

import io.jsonwebtoken.io.IOException;


import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InvoiceIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private UserService userService;


     String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsInN1YiI6ImFiYyIsImlhdCI6MTY5ODM0MTI0MywiZXhwIjoxNjk4MzQzMDQzfQ.4kCcXDaTTJpuiffp45rEfcQMDkkcg0dLbZ-ceLVq7IQ";

    private static HttpHeaders headers;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    private String createURLWithPort(String path) {
        return "http://localhost:" + port + "/invoice" + path;
    }

    @Test
    public void testGetAllInvoicesByUserId() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);


        HttpEntity<String> entity = new HttpEntity<>(null, headers);


        ResponseEntity<List<Invoice>> response = restTemplate.exchange(
            createURLWithPort("/get/invoice"), HttpMethod.GET, entity, new ParameterizedTypeReference<List<Invoice>>() {});


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
       
    }


    @Test
    public void testDisplayPdf() throws IOException {
 
        InvoiceIdDTO invoiceIdDTO = new InvoiceIdDTO();
        invoiceIdDTO.setInvoiceId(1L); 

        ResponseEntity<InputStreamResource> response = restTemplate.postForEntity(
            createURLWithPort("/displayPdf"), invoiceIdDTO, InputStreamResource.class);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(MediaType.APPLICATION_PDF, response.getHeaders().getContentType());
        // assertEquals(sampleFile.length(), Objects.requireNonNull(response.getBody()).contentLength());
    
    }

    @Test
    public void testWriteInvoice() {
        // Mock the necessary DTOs and parameters
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setEmailId("handsompikul04@gmail.com");
        invoiceDTO.setPlanDescription("Basic Party Plan");
        invoiceDTO.setPlanId(4L);
        invoiceDTO.setPrice(320.00);
        List<NameAndPhone> nameAndPhones = new ArrayList<>();

        NameAndPhone nameAndPhone = new NameAndPhone();
        nameAndPhone.setName("Bibhu");
        nameAndPhone.setPhoneNo("+91894385732");
        
        nameAndPhones.add(nameAndPhone);
        
        nameAndPhone.setName("Gaurav");
        nameAndPhone.setName("+918596859495");
        
        nameAndPhones.add(nameAndPhone);
        invoiceDTO.setNameAndPhones(nameAndPhones);

        List<Ott> otts = new ArrayList<>();
    
        Ott ott = new Ott();
        ott.setOttId(10L);
        ott.setOttName("Disney+");
        ott.setPlanTitle("Premium");
        otts.add(ott);
        invoiceDTO.setOtts(otts);
    
        
        GenerateInvoiceDTO generateInvoiceDTO = new GenerateInvoiceDTO();
        generateInvoiceDTO.setBillId(45);
        generateInvoiceDTO.setPlanId(4);
        generateInvoiceDTO.setUserId(5);
        generateInvoiceDTO.setFinalPrice(288);
      

        PdfService pdfService = new PdfService();

        pdfService.WriteInvoice(invoiceDTO, generateInvoiceDTO);

    }

    @Test
    public void testGenerateNewPDF() throws JsonProcessingException {
        // Create a sample list of InvoiceDTOs
        List<InvoiceDTO> invoiceDTOs = new ArrayList<>();
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setEmailId("handsompikul04@gmail.com");
        invoiceDTO.setPlanDescription("Basic Party Plan");
        invoiceDTO.setPlanId(4L);
        invoiceDTO.setPrice(320.00);
        List<NameAndPhone> nameAndPhones = new ArrayList<>();

        NameAndPhone nameAndPhone = new NameAndPhone();
        nameAndPhone.setName("Bibhu");
        nameAndPhone.setPhoneNo("+91894385732");
        
        nameAndPhones.add(nameAndPhone);
        
        nameAndPhone.setName("Gaurav");
        nameAndPhone.setName("+918596859495");
        
        nameAndPhones.add(nameAndPhone);
        invoiceDTO.setNameAndPhones(nameAndPhones);

        List<Ott> otts = new ArrayList<>();
        
        Ott ott = new Ott();
        ott.setOttId(10L);
        ott.setOttName("Disney+");
        ott.setPlanTitle("Premium");

        otts.add(ott);

        invoiceDTO.setOtts(otts);
        invoiceDTOs.add( invoiceDTO);

        // Convert the list of InvoiceDTOs to JSON
        String jsonRequest = objectMapper.writeValueAsString(invoiceDTOs);

        // Define the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create an HTTP entity with the JSON request and headers
        HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);

        // Mock the necessary methods and dependencies
        // For example, you can mock the pdfService.WriteInvoice method and verify its behavior

        // Send a POST request to the endpoint
        ResponseEntity<String> response = restTemplate.exchange(
            createURLWithPort("/create"), HttpMethod.POST, entity, String.class);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Bill and Invoice Generated", response.getBody());
    }



    
}
