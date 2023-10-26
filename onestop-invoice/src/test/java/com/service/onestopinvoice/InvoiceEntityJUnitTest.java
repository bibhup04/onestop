package com.service.onestopinvoice;

import org.junit.jupiter.api.Test;

import com.service.onestopinvoice.DTO.UserDTO;
import com.service.onestopinvoice.entity.Invoice;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceEntityJUnitTest {

     @Test
    public void testInvoiceFields() {
        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setPlanId(123L);
        invoice.setPlanDescription("Sample plan description");
        invoice.setFinalPrice(100.0);
        invoice.setEmailId("sample@example.com");
        invoice.setUserId(456L);
        invoice.setUserName("Sample User");
        invoice.setBillId(789L);
        invoice.setPath("/sample/path");
        //assertNotNull(invoice.getCreatedAt());
        
        assertEquals(1L, invoice.getId());
        assertEquals(123L, invoice.getPlanId());
        assertEquals("Sample plan description", invoice.getPlanDescription());
        assertEquals(100.0, invoice.getFinalPrice());
        assertEquals("sample@example.com", invoice.getEmailId());
        assertEquals(456L, invoice.getUserId());
        assertEquals("Sample User", invoice.getUserName());
        assertEquals(789L, invoice.getBillId());
        assertEquals("/sample/path", invoice.getPath());
    }

    @Test
    public void testUserDTOGettersAndSetters() {
        // Given
        Long id = 1L;
        String name = "John Doe";
        String email = "johndoe@example.com";
        String phoneNo = "1234567890";
        String password = "password123";
        String role = "user";
        String status = "active";

        // When
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setName(name);
        userDTO.setEmail(email);
        userDTO.setPhoneNo(phoneNo);
        userDTO.setPassword(password);
        userDTO.setRole(role);
        userDTO.setStatus(status);

        // Then
        assertEquals(id, userDTO.getId());
        assertEquals(name, userDTO.getName());
        assertEquals(email, userDTO.getEmail());
        assertEquals(phoneNo, userDTO.getPhoneNo());
        assertEquals(password, userDTO.getPassword());
        assertEquals(role, userDTO.getRole());
        assertEquals(status, userDTO.getStatus());
    }
    
}
