package com.service.onestopinvoice;

import org.junit.jupiter.api.Test;

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
    
}
