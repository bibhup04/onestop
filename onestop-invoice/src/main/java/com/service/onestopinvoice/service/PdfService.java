package com.service.onestopinvoice.service;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import com.service.onestopinvoice.DTO.GenerateInvoiceDTO;
import com.service.onestopinvoice.DTO.InvoiceDTO;
import com.service.onestopinvoice.DTO.NameAndPhone;
import com.service.onestopinvoice.DTO.Ott;

@Service
public class PdfService {

  PDDocument invc;

  
  /** 
   * @param invoiceDTO
   * @param generateInvoiceDTO
   * @return String
   */
  public String WriteInvoice(InvoiceDTO invoiceDTO, GenerateInvoiceDTO generateInvoiceDTO) {
    PDDocument invc = new PDDocument();
    PDPage mypage = new PDPage();
    invc.addPage(mypage);
    
    try {
      // Prepare Content Stream
      PDPageContentStream cs = new PDPageContentStream(invc, mypage);

      

      // Create a light grey-colored box
      float boxWidth = mypage.getMediaBox().getWidth(); // full page width
      float boxHeight = 180; 
      float boxStartX = 0; 
      float boxStartY = mypage.getMediaBox().getHeight() - boxHeight; 
      Color lightGrey = new Color(192, 192, 192); 

      cs.setNonStrokingColor(lightGrey); // Set the fill color to light grey
      cs.addRect(boxStartX, boxStartY, boxWidth, boxHeight);
      cs.fill(); // Fill the rectangle with the specified color

      // ... Existing code

        // Load the image
        File file = new File("/home/bibhu04/Microservices/onestop/onestop-invoice/logo.png"); // Replace "path_to_your_image_file" with the actual path to your image
        PDImageXObject pdImage = PDImageXObject.createFromFileByExtension(file, invc);
       
        float imageWidth = 200; 
        float imageHeight = 100; 
        float imageX = 380; 
        float imageY = 655; 

        // Draw the image onto the page
        cs.drawImage(pdImage, imageX, imageY, imageWidth, imageHeight);



      // Invoice
      cs.beginText();
      cs.setFont(PDType1Font.HELVETICA_BOLD, 36); 
      cs.newLineAtOffset(40, boxStartY + 20); 
      cs.setNonStrokingColor(Color.BLACK); 
      cs.showText("INVOICE"); 
      cs.endText();

    cs.beginText();
    cs.setFont(PDType1Font.TIMES_ROMAN, 12);
    cs.newLineAtOffset(40, boxStartY + 130); // Adjusted the position for the top-left corner
    cs.showText("Invoice ID: " + generateInvoiceDTO.getBillId());
    cs.setNonStrokingColor(Color.BLACK); 

    // Get the current date and time
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    String formattedDate = dateFormat.format(date);
    cs.newLineAtOffset(0, -15); // Adjust for the next line
    cs.showText("Date: " + formattedDate);
    cs.setNonStrokingColor(Color.BLACK); 
    cs.endText();


      // Display Name and Phone Numbers
      List<NameAndPhone> nameAndPhones = invoiceDTO.getNameAndPhones();
      float startNameY = boxStartY - 20; 
      float startNameX = 40; 

      // Print the email ID 
      cs.beginText();
      cs.setFont(PDType1Font.HELVETICA_BOLD, 18); // Use a bold font for the Email ID
      cs.setNonStrokingColor(Color.DARK_GRAY); // Set the text color
      cs.newLineAtOffset(startNameX, startNameY);
      cs.showText("Email ID: ");
      cs.setFont(PDType1Font.TIMES_ROMAN, 18); // Revert back to the regular font for the email address
      cs.showText(invoiceDTO.getEmailId());
      cs.endText();


      startNameY -= 30; // Adjust for the next line

      // Set the font and color for the names and phone numbers
      cs.setFont(PDType1Font.HELVETICA_BOLD, 16);
      cs.setNonStrokingColor(Color.BLACK); // Set the text color

      for (NameAndPhone nameAndPhone : nameAndPhones) {
          cs.beginText();
          cs.setFont(PDType1Font.HELVETICA_BOLD, 16);
          cs.newLineAtOffset(startNameX, startNameY);
          cs.showText("Name: ");
          cs.setFont(PDType1Font.TIMES_ROMAN, 16);
          cs.showText(nameAndPhone.getName());
          cs.newLineAtOffset(0, -20); // Adjust for the next line
          cs.setFont(PDType1Font.HELVETICA_BOLD, 16);
          cs.showText("Phone: ");
          cs.setFont(PDType1Font.TIMES_ROMAN, 16);
          cs.showText(nameAndPhone.getPhoneNo());
          cs.endText();
          startNameY -= 40; // Adjust for the next set of data
      }


        // Draw table rows
        float tableTopY = 300; // Adjusted the table top

        // ... Existing code

        // Draw a box around the table headers
        float headerBoxWidth = 520;
        float headerBoxHeight = 30;
        float headerBoxStartX = 40;
        float headerBoxStartY = tableTopY - 7;

        cs.addRect(headerBoxStartX, headerBoxStartY, headerBoxWidth, headerBoxHeight);
        cs.setStrokingColor(Color.BLACK);
        cs.setLineWidth(1);
        cs.stroke();

        // Draw table headers
        cs.setFont(PDType1Font.HELVETICA_BOLD, 16);
        cs.beginText();
        cs.newLineAtOffset(80, tableTopY );
        cs.showText("Plan Name");
        cs.newLineAtOffset(220, 0);
        cs.showText("Service");
        cs.newLineAtOffset(150, 0);
        cs.showText("Price");
        cs.endText();

        // ... Remaining code


        // Draw table rows
        float rowY = tableTopY - 20; // Adjusted the rowY

        cs.setFont(PDType1Font.TIMES_ROMAN, 14);
        cs.beginText();
        cs.newLineAtOffset(80, rowY);
        cs.showText(invoiceDTO.getPlanDescription());
        cs.endText();

        List<Ott> otts = invoiceDTO.getOtts();
        for (Ott ott : otts) {
            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 14);
            cs.newLineAtOffset(300, rowY);
            cs.showText(ott.getOttName());
            cs.endText();
            rowY -= 20;
        }

        // Draw the price
        rowY -= 20;

        cs.beginText();
        cs.setFont(PDType1Font.TIMES_ROMAN, 14);
        cs.newLineAtOffset(450, rowY);
        cs.showText("Rs." + invoiceDTO.getPrice());
        cs.endText();

        cs.setStrokingColor(Color.BLACK);
        cs.setLineWidth(1);
        cs.moveTo(420, rowY - 15); // Starting point of the line
        cs.lineTo(530, rowY - 15); // Ending point of the line
        cs.stroke();

        // Print "Discount"
        rowY -= 30;

        cs.beginText();
        cs.setFont(PDType1Font.TIMES_ROMAN, 14);
        cs.newLineAtOffset(280, rowY);
        cs.showText("Discount");
        cs.endText();

        // Print the discount amount
        cs.beginText();
        cs.setFont(PDType1Font.TIMES_ROMAN, 14);
        cs.newLineAtOffset(450, rowY);
        double discount = invoiceDTO.getPrice() - generateInvoiceDTO.getFinalPrice();
        String formattedDiscount = String.format("%.2f", discount);

        discount = Double.parseDouble(formattedDiscount);
        cs.showText("Rs." + Double.toString(discount));
        cs.endText();

        // Print the total amount
        rowY -= 20;

        cs.beginText();
        cs.setFont(PDType1Font.TIMES_ROMAN, 14);
        cs.newLineAtOffset(280, rowY);
        cs.showText("Total");
        cs.endText();

        cs.beginText();
        cs.setFont(PDType1Font.TIMES_ROMAN, 14);
        cs.newLineAtOffset(450, rowY);
        cs.showText("Rs." + Double.toString(generateInvoiceDTO.getFinalPrice()));
        cs.endText();

        // ... Rest of the code

        // Close the content stream
        cs.close();

        // ... Rest of the code

      


      // Close the content stream
      cs.close();
      String currentDirectory = System.getProperty("user.dir");
      String pdfFilePath = currentDirectory + "/invoice/" + invoiceDTO.getNameAndPhones().get(0).getName() + "_" + generateInvoiceDTO.getBillId() + ".pdf";


     // String pdfFilePath = "/home/bibhu04/Microservices/onestop/onestop-invoice/invoice/customer_details.pdf";
      invc.save(pdfFilePath);
      return pdfFilePath;

    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }

}

