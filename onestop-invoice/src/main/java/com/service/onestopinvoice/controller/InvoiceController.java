package com.service.onestopinvoice.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.onestopinvoice.DTO.GenerateInvoiceDTO;
import com.service.onestopinvoice.DTO.InvoiceDTO;
import com.service.onestopinvoice.DTO.InvoiceIdDTO;
import com.service.onestopinvoice.DTO.UserDTO;
import com.service.onestopinvoice.entity.Invoice;
import com.service.onestopinvoice.service.AppService;
import com.service.onestopinvoice.service.EmailSenderService;
import com.service.onestopinvoice.service.InvoiceService;
import com.service.onestopinvoice.service.PdfService;
import com.service.onestopinvoice.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/invoice")
@Tag(name = "Invoice controller", description = "It creates invoice and sends invoice via mail to user.")
public class InvoiceController {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private UserService userService;

    @Autowired
    private AppService appService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private EmailSenderService emailSenderService;


     
     /** 
      * @param token
      * @return ResponseEntity<List<Invoice>>
      */
     @GetMapping("/get/invoice")
    @Operation(summary = "User Details", description = "It is a test endpoint to test feing communication and recieve userdetails.")
    public ResponseEntity<List<Invoice>> createFamilyDetails(@RequestHeader("Authorization") String token){
        UserDTO userDTO = userService.getUserDetails(token);
        List<Invoice> invoices = invoiceService.getAllInvoicesByUserId(userDTO.getId());
        return new ResponseEntity<>( invoices, HttpStatus.OK);
    }


    @PostMapping("/generate-invoice")
    @Operation(summary = "Generate Invoice", description = "It recieves billId, SubscriptionId and userID and again send userId to authService and recieve all the details and generate Invoice and send to user via mail.")
    public ResponseEntity<String> generateInvoice(@RequestBody List<GenerateInvoiceDTO> generateInvoiceDTOList) {

    List<InvoiceDTO> invoiceDTOs = appService.getInvoiceDetails(generateInvoiceDTOList);

    if (invoiceDTOs != null) {
        for (int i = 0; i < invoiceDTOs.size(); i ++) {
            String pdfFilePath = pdfService.WriteInvoice(invoiceDTOs.get(i), generateInvoiceDTOList.get(i));
            invoiceService.addInvoice(invoiceDTOs.get(i), generateInvoiceDTOList.get(i), pdfFilePath);

            try {
                emailSenderService.sendMailWithAttachment(invoiceDTOs.get(i).getEmailId(),
                "Dear Customer,\n\nWe are pleased to provide you with your invoice for this month's Postpaid OTT subscription. Please ensure timely payment to avoid any inconvenience. If you have any questions or need further assistance, please feel free to reach out to us at any time.\n\n" + //
                        "Thank you for choosing our services.\n\nWarm regards,\nOnestop",
                "Your Monthly Postpaid OTT Subscription Invoice", "" +
                        pdfFilePath);
                } catch (MessagingException e) {
                    
                    e.printStackTrace(); 
                }       
        }
    } else {
        System.out.println("InvoiceDTO list is null.");
    }

    return new ResponseEntity<>("Invoice Generated", HttpStatus.OK);
}

    @PostMapping("/create")
    @Operation(summary = "Create Invoice", description = "It is a test endpoint to create invoice for given data.")
    public ResponseEntity<String> generateNewPDF(@RequestBody List<InvoiceDTO> invoiceDTOs){

        if (invoiceDTOs != null) {
            for (InvoiceDTO invoiceDTO : invoiceDTOs) {
                GenerateInvoiceDTO generateInvoiceDTO = new GenerateInvoiceDTO();
                generateInvoiceDTO.setBillId(32);
                generateInvoiceDTO.setFinalPrice(500.34);
                
                
                
                String pdfFilePath = pdfService.WriteInvoice(invoiceDTO,  generateInvoiceDTO);
                
                 try {
                    emailSenderService.sendMailWithAttachment("handsompikul04@gmail.com",
                        "Dear Customer,\n\nWe are pleased to provide you with your invoice for this month's Postpaid OTT subscription. Please ensure timely payment to avoid any inconvenience. If you have any questions or need further assistance, please feel free to reach out to us at any time.\n\n" + //
                        "Thank you for choosing our services.\n\nWarm regards,\nOnestop",
                "Your Monthly Postpaid OTT Subscription Invoice", "" +
                        pdfFilePath);
                } catch (MessagingException e) {
                    
                    e.printStackTrace(); 
                }        
            }
        } else {
            System.out.println("InvoiceDTO list is null.");
        }
        return new ResponseEntity<>( "Bill and Invoice Generated" , HttpStatus.OK);
    }

    @Operation(summary = "Display PDF", description = "It recieves invoiceId and in return returns the invoice.")
    @PostMapping("/displayPdf")
    public ResponseEntity<InputStreamResource> displayPdf(@RequestBody InvoiceIdDTO invoiceIdDTO) throws IOException {
        Invoice invoice = invoiceService.getInvoiceById(invoiceIdDTO.getInvoiceId()).get();
        
        String pdfFilePath = invoice.getPath();
        Path path = Paths.get(pdfFilePath);
        File file = path.toFile();

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }

    
}
