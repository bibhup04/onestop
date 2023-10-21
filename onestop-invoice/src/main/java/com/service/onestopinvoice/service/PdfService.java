package com.service.onestopinvoice.service;




import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;




@Service
public class PdfService {

    PDDocument invc;
    int n = 3; // Assuming 3 products in the list
        Integer total = 0;
        Integer price;
        String CustName = "John Doe";
        String CustPh = "1234567890";
        // List<String> ProductName = new ArrayList<>();
        // List<Integer> ProductPrice = new ArrayList<>();
        // List<Integer> ProductQty = new ArrayList<>();
        String InvoiceTitle = "CodeSpeedy Technology Private Limited";
        String SubTitle = "Invoice";

    

    public void WriteInvoice() {
        PDDocument invc = new PDDocument();
       
        PDPage mypage = new PDPage();
        invc.addPage(mypage);

        try {
          //Prepare Content Stream
          PDPageContentStream cs = new PDPageContentStream(invc, mypage);

          // Add header
        cs.beginText();
        cs.setFont(PDType1Font.TIMES_ROMAN, 12);
        cs.newLineAtOffset(100, 800);
        cs.showText("Header Text");
        cs.endText();

        // Add footer
        cs.beginText();
        cs.setFont(PDType1Font.TIMES_ROMAN, 12);
        cs.newLineAtOffset(100, 50);
        cs.showText("Footer Text");
        cs.endText();

          
          //Writing Single Line text
          //Writing the Invoice title
          cs.beginText();
          cs.setFont(PDType1Font.TIMES_ROMAN, 20);
          cs.newLineAtOffset(140, 750);
          cs.showText(InvoiceTitle);
          cs.endText();
          
          cs.beginText();
          cs.setFont(PDType1Font.TIMES_ROMAN, 18);
          cs.newLineAtOffset(270, 690);
          cs.showText(SubTitle);
          cs.endText();
          
          //Writing Multiple Lines
          //writing the customer details
          cs.beginText();
          cs.setFont(PDType1Font.TIMES_ROMAN, 14);
          cs.setLeading(20f);
          cs.newLineAtOffset(60, 610);
          cs.showText("Customer Name: ");
          cs.newLine();
          cs.showText("Phone Number: ");
          cs.endText();
          
          cs.beginText();
          cs.setFont(PDType1Font.TIMES_ROMAN, 14);
          cs.setLeading(20f);
          cs.newLineAtOffset(170, 610);
          cs.showText(CustName);
          cs.newLine();
          cs.showText(CustPh);
          cs.endText();
          
          cs.beginText();
          cs.setFont(PDType1Font.TIMES_ROMAN, 14);
          cs.newLineAtOffset(80, 540);
          cs.showText("Product Name");
          cs.endText();
          
          cs.beginText();
          cs.setFont(PDType1Font.TIMES_ROMAN, 14);
          cs.newLineAtOffset(200, 540);
          cs.showText("Unit Price");
          cs.endText();
          
          cs.beginText();
          cs.setFont(PDType1Font.TIMES_ROMAN, 14);
          cs.newLineAtOffset(310, 540);
          cs.showText("Quantity");
          cs.endText();
          
          cs.beginText();
          cs.setFont(PDType1Font.TIMES_ROMAN, 14);
          cs.newLineAtOffset(410, 540);
          cs.showText("Price");
          cs.endText();
          
          
          cs.beginText();
          cs.setFont(PDType1Font.TIMES_ROMAN, 14);
          cs.newLineAtOffset(310, (500-(20*n)));
          cs.showText("Total: ");
          cs.endText();
          
          cs.beginText();
          cs.setFont(PDType1Font.TIMES_ROMAN, 14);
          //Calculating where total is to be written using number of products
          cs.newLineAtOffset(410, (500-(20*n)));
          cs.showText(total.toString());
          cs.endText();


    // Draw table rows
    float tableTopY = 500; // adjust the Y-coordinate according to your requirement

    // Draw table headers
    cs.beginText();
    cs.setFont(PDType1Font.TIMES_ROMAN, 14);
    cs.newLineAtOffset(80, tableTopY);
    cs.showText("Plan Name");
    cs.newLineAtOffset(150, 0);
    cs.showText("Service");
    cs.newLineAtOffset(150, 0);
    cs.showText("Price");
    cs.endText();

    // Draw table rows
    float rowY = tableTopY - 20; // adjust the row height and Y-coordinate according to your requirement

    cs.beginText();
    cs.setFont(PDType1Font.TIMES_ROMAN, 14);
    cs.newLineAtOffset(80, rowY);
    cs.showText("Premium Couple Plan");
    cs.newLineAtOffset(150, 0);
    cs.showText("Netflix");
    cs.newLineAtOffset(150, 0);
    cs.showText("300");
    cs.endText();

    rowY -= 20; // adjust the row height according to your requirement

    cs.beginText();
    cs.setFont(PDType1Font.TIMES_ROMAN, 14);
    cs.newLineAtOffset(80, rowY);
    cs.showText("");
    cs.newLineAtOffset(150, 0);
    cs.showText("Amazon Prime");
    cs.newLineAtOffset(150, 0);
    cs.showText("200");
    cs.endText();

    // Draw the total
    rowY -= 30; // adjust the row height according to your requirement

    cs.beginText();
    cs.setFont(PDType1Font.TIMES_ROMAN, 14);
    cs.newLineAtOffset(80, rowY);
    cs.showText("Total");
    cs.newLineAtOffset(150, 0);
    cs.showText("");
    cs.newLineAtOffset(150, 0);
    cs.showText("500");
    cs.endText();


          //Close the content stream
          cs.close();
          String pdfFilePath = "/home/bibhu04/Microservices/onestop/onestop-invoice/customer_details.pdf";
          invc.save(pdfFilePath);
          
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    
}
