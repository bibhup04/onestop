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

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/invoice")
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


     @GetMapping("/get/invoice")
    public ResponseEntity<List<Invoice>> createFamilyDetails(@RequestHeader("Authorization") String token){
        System.out.println("token - " + token);
        UserDTO userDTO = userService.getUserDetails(token);
        List<Invoice> invoices = invoiceService.getAllInvoicesByUserId(userDTO.getId());
        return new ResponseEntity<>( invoices, HttpStatus.OK);
    }
    


    @PostMapping("/generate-invoice")
    public ResponseEntity<String> generateInvoice(@RequestBody List<GenerateInvoiceDTO> generateInvoiceDTOList) {
    for (GenerateInvoiceDTO generateInvoiceDTO : generateInvoiceDTOList) {
        System.out.println("Received GenerateInvoiceDTO from postman:");
        System.out.println("Bill Id: " + generateInvoiceDTO.getBillId());
        System.out.println("User Id: " + generateInvoiceDTO.getUserId());
        System.out.println("Plan Id: " + generateInvoiceDTO.getPlanId());
        System.out.println("Final Price: " + generateInvoiceDTO.getFinalPrice());
    }

    List<InvoiceDTO> invoiceDTOs = appService.getInvoiceDetails(generateInvoiceDTOList);

    if (invoiceDTOs != null) {
        for (int i = 0; i < invoiceDTOs.size(); i ++) {
            System.out.println("plan description - " + invoiceDTOs.get(i).getPlanDescription());
            String pdfFilePath = pdfService.WriteInvoice(invoiceDTOs.get(i), generateInvoiceDTOList.get(i));
            invoiceService.addInvoice(invoiceDTOs.get(i), generateInvoiceDTOList.get(i), pdfFilePath);

            try {
                emailSenderService.sendMailWithAttachment(invoiceDTOs.get(i).getEmailId(),
                "Here is your invoice",
                "Your monthly invoice", "" +
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
    public ResponseEntity<String> generateNewPDF(@RequestBody List<InvoiceDTO> invoiceDTOs){

        if (invoiceDTOs != null) {
            for (InvoiceDTO invoiceDTO : invoiceDTOs) {
                GenerateInvoiceDTO generateInvoiceDTO = new GenerateInvoiceDTO();
                generateInvoiceDTO.setBillId(32);
                generateInvoiceDTO.setFinalPrice(500.34);
                
                System.out.println("InvoiceDTO - plan desc: " + invoiceDTO.getPlanDescription());
                System.out.println("InvoiceDTO - name and phone: " + invoiceDTO.getNameAndPhones());
                System.out.println("InvoiceDTO - Plan Id: " + invoiceDTO.getPlanId());
                System.out.println("InvoiceDTO - emailId: " + invoiceDTO.getEmailId());
                
                String pdfFilePath = pdfService.WriteInvoice(invoiceDTO,  generateInvoiceDTO);
                
                //  try {
                //     emailSenderService.sendMailWithAttachment("handsompikul04@gmail.com",
                // "Here is your invoice",
                // "Your monthly invoice", "" +
                //         pdfFilePath);
                // } catch (MessagingException e) {
                    
                //     e.printStackTrace(); 
                // }        
            }
        } else {
            System.out.println("InvoiceDTO list is null.");
        }

        //pdfService.WriteInvoice();

        return new ResponseEntity<>( "Bill and Invoice Generated" , HttpStatus.OK);
    }

    //  @GetMapping("/generate")
    // public ResponseEntity<InputStreamResource> getAllSubscriptions() {

    //     ByteArrayInputStream pdf = pdfService.createPdf();

    //     HttpHeaders httpHeaders = new HttpHeaders();

    //     httpHeaders.add("Content-Disposition", "inline;file=lcwd.pdf");



    //     return ResponseEntity.ok()
    //         .headers(httpHeaders)
    //         .contentType(MediaType.APPLICATION_PDF)
    //         .body(new InputStreamResource(pdf));
    // }

   

    @PostMapping("/displayPdf")
    public ResponseEntity<InputStreamResource> displayPdf(@RequestBody InvoiceIdDTO invoiceIdDTO) throws IOException {
        Invoice invoice = invoiceService.getInvoiceById(invoiceIdDTO.getInvoiceId()).get();
        
        // String currentDirectory = System.getProperty("user.dir");
        // String pdfFilePath = currentDirectory + "/invoice/customer_details.pdf";
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
