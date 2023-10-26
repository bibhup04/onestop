package com.service.onestopinvoice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.service.onestopinvoice.DTO.GenerateInvoiceDTO;
import com.service.onestopinvoice.DTO.InvoiceDTO;
import com.service.onestopinvoice.DTO.NameAndPhone;
import com.service.onestopinvoice.DTO.Ott;
import com.service.onestopinvoice.feignclint.AppServiceClient;
import com.service.onestopinvoice.repository.InvoiceRepository;
import com.service.onestopinvoice.service.AppService;
import com.service.onestopinvoice.service.InvoiceService;
import com.service.onestopinvoice.service.PdfService;

public class AppServiceJUnitTest {

    private AppService appService;
    private AppServiceClient appServiceClient;

    @InjectMocks
    private PdfService pdfService;

    @Mock
    private InvoiceDTO invoiceDTO;

    @Mock
    private GenerateInvoiceDTO generateInvoiceDTO;

    @BeforeEach
    public void setup() {
        appServiceClient = mock(AppServiceClient.class);
        appService = new AppService(appServiceClient);
       // appService.setAppServiceClient(appServiceClient);
    }

   

    @Test
    public void testGetInvoiceDetails() {
        List<GenerateInvoiceDTO> generateInvoiceDTOs = new ArrayList<>();
        GenerateInvoiceDTO generateInvoiceDTO = new GenerateInvoiceDTO();
        generateInvoiceDTO.setBillId(45);
        generateInvoiceDTO.setPlanId(4);
        generateInvoiceDTO.setUserId(5);
        generateInvoiceDTO.setFinalPrice(288);
        generateInvoiceDTOs.add(generateInvoiceDTO);


        List<InvoiceDTO> mockInvoiceDTOs = new ArrayList<>();
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
        mockInvoiceDTOs.add( invoiceDTO);

        ResponseEntity<List<InvoiceDTO>> mockResponseEntity = new ResponseEntity<>(mockInvoiceDTOs, HttpStatus.OK);

        when(appServiceClient.generateInvoice(generateInvoiceDTOs)).thenReturn(mockResponseEntity);

        List<InvoiceDTO> result = appService.getInvoiceDetails(generateInvoiceDTOs);

        assertEquals(mockInvoiceDTOs.size(), result.size());
        assertEquals(mockInvoiceDTOs.get(0).getPlanDescription() , result.get(0).getPlanDescription());
        
        // Additional assertEquals statements for other fields in InvoiceDTO
        assertEquals(mockInvoiceDTOs.get(0).getEmailId(), result.get(0).getEmailId());
        assertEquals(mockInvoiceDTOs.get(0).getPlanId(), result.get(0).getPlanId());
        assertEquals(mockInvoiceDTOs.get(0).getPrice(), result.get(0).getPrice());
        
        // Check the first name and phone in the list
        assertEquals(mockInvoiceDTOs.get(0).getNameAndPhones().get(0).getName(), result.get(0).getNameAndPhones().get(0).getName());
        assertEquals(mockInvoiceDTOs.get(0).getNameAndPhones().get(0).getPhoneNo(), result.get(0).getNameAndPhones().get(0).getPhoneNo());
        
        // Check the second name and phone in the list
        assertEquals(mockInvoiceDTOs.get(0).getNameAndPhones().get(1).getName(), result.get(0).getNameAndPhones().get(1).getName());
        assertEquals(mockInvoiceDTOs.get(0).getNameAndPhones().get(1).getPhoneNo(), result.get(0).getNameAndPhones().get(1).getPhoneNo());

        // Check OTT details
        assertEquals(mockInvoiceDTOs.get(0).getOtts().get(0).getOttId(), result.get(0).getOtts().get(0).getOttId());
        assertEquals(mockInvoiceDTOs.get(0).getOtts().get(0).getOttName(), result.get(0).getOtts().get(0).getOttName());
        assertEquals(mockInvoiceDTOs.get(0).getOtts().get(0).getPlanTitle(), result.get(0).getOtts().get(0).getPlanTitle());

        assertEquals(generateInvoiceDTO.getBillId(), generateInvoiceDTO.getBillId());
        assertEquals(generateInvoiceDTO.getFinalPrice(), generateInvoiceDTO.getFinalPrice());
        assertEquals(generateInvoiceDTO.getPlanId(), generateInvoiceDTO.getPlanId());
        assertEquals(generateInvoiceDTO.getUserId(), generateInvoiceDTO.getUserId());
    }

  
    
}
