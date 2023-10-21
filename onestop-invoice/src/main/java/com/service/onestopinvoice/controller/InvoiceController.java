package com.service.onestopinvoice.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.onestopinvoice.DTO.GenerateInvoiceDTO;
import com.service.onestopinvoice.service.PdfService;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private PdfService pdfService;


    @PostMapping("/generate-invoice")
    public ResponseEntity<String> generateInvoice(@RequestBody GenerateInvoiceDTO generateInvoiceDTO) {
        System.out.println("Received GenerateInvoiceDTO:");
        System.out.println("Bill Id: " + generateInvoiceDTO.getBillId());
        System.out.println("User Id: " + generateInvoiceDTO.getUserId());
        System.out.println("Plan Id: " + generateInvoiceDTO.getPlanId());
        System.out.println("Final Price: " + generateInvoiceDTO.getFinalPrice());
        return new ResponseEntity<>( "Invoice Generated" , HttpStatus.OK);
    }

    @GetMapping("/create")
    public ResponseEntity<String> generateNewPDF(){
        pdfService.WriteInvoice();

        return new ResponseEntity<>( "Invoice Generated" , HttpStatus.OK);
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
    

    @GetMapping("/displayPdf")
    public ResponseEntity<InputStreamResource> displayPdf() throws IOException {
        String pdfFilePath = "/home/bibhu04/Microservices/onestop/onestop-invoice/customer_details.pdf";
        Path path = Paths.get(pdfFilePath);
        File file = path.toFile();

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }
}
