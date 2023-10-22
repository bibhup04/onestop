package com.service.onestopinvoice.service;

import java.awt.Color;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import com.service.onestopinvoice.DTO.GenerateInvoiceDTO;
import com.service.onestopinvoice.DTO.InvoiceDTO;
import com.service.onestopinvoice.DTO.NameAndPhone;
import com.service.onestopinvoice.DTO.Ott;

@Service
public class PdfService {

  PDDocument invc;

  public void WriteInvoice(InvoiceDTO invoiceDTO, double finalPrice) {
    PDDocument invc = new PDDocument();
    PDPage mypage = new PDPage();
    invc.addPage(mypage);

    try {
      // Prepare Content Stream
      PDPageContentStream cs = new PDPageContentStream(invc, mypage);

      // Create a light grey-colored box
      float boxWidth = mypage.getMediaBox().getWidth(); // full page width
      float boxHeight = 180; // fixed height
      float boxStartX = 0; // start from the extreme left
      float boxStartY = mypage.getMediaBox().getHeight() - boxHeight; // start from the top
      Color lightGrey = new Color(192, 192, 192); // define light grey color

      cs.setNonStrokingColor(lightGrey); // Set the fill color to light grey
      cs.addRect(boxStartX, boxStartY, boxWidth, boxHeight);
      cs.fill(); // Fill the rectangle with the specified color

      // Invoice
      cs.beginText();
      cs.setFont(PDType1Font.HELVETICA_BOLD, 36); // Change font and size for big letters
      cs.newLineAtOffset(40, boxStartY + 20); // Adjust the position as needed
      cs.setNonStrokingColor(Color.BLACK); // Set the text color to black
      cs.showText("INVOICE"); // Write "INVOICE" in big letters
      cs.endText();

      // ONESTOP in designed font
      cs.beginText();
      cs.setFont(PDType1Font.TIMES_BOLD_ITALIC, 56); // Choose a suitable designed font and size
      String onestopText = "ONESTOP"; // Replace with the desired designed text
      float stringWidth = (float) PDType1Font.TIMES_BOLD_ITALIC.getStringWidth(onestopText) / 1000 * 36;
      float startX = boxWidth - stringWidth - 140; // Position it to the top right corner
      cs.newLineAtOffset(startX, boxStartY + 75);
      cs.setNonStrokingColor(Color.BLACK); // Set the text color to black
      cs.showText(onestopText); // Write "ONESTOP" in designed font
      cs.endText();

      // ... Existing code

      // Display Name and Phone Numbers
      List<NameAndPhone> nameAndPhones = invoiceDTO.getNameAndPhones();
      float startNameY = boxStartY - 20; // Position the text below the box
      float startNameX = 40; // Adjust the start position as needed

      // Print the email ID above the names and phone numbers
      cs.beginText();
      cs.setFont(PDType1Font.TIMES_ROMAN, 18);
      cs.newLineAtOffset(startNameX, startNameY);
      cs.showText("Email ID: " + invoiceDTO.getEmailId());
      cs.endText();

      startNameY -= 30; // Adjust for the next line

      for (NameAndPhone nameAndPhone : nameAndPhones) {
          cs.beginText();
          cs.setFont(PDType1Font.TIMES_ROMAN, 18);
          cs.newLineAtOffset(startNameX, startNameY);
          cs.showText("Name: " + nameAndPhone.getName());
          cs.newLineAtOffset(0, -15); // Adjust for the next line
          cs.showText("Phone: " + nameAndPhone.getPhoneNo());
          cs.endText();
          startNameY -= 30; // Adjust for the next set of data
      }


      // Define the box dimensions and properties
      // float tableBoxWidth = 500;
      // float tableBoxHeight = 350;
      // float tableBoxStartX = 40;
      // float tableBoxStartY = 150;
      // Color blackColor = new Color(0, 0, 0); // define black color

      // // Draw the box
      // cs.addRect(tableBoxStartX, tableBoxStartY, tableBoxWidth, tableBoxHeight);
      // cs.setStrokingColor(blackColor);
      // cs.setLineWidth(2);
      // cs.stroke();


      // Draw table rows
      float tableTopY = 400; // adjust the Y-coordinate according to your
      // requirement

      // Draw table headers
      cs.beginText();
      cs.setFont(PDType1Font.TIMES_ROMAN, 16);
      cs.newLineAtOffset(80, tableTopY);
      cs.showText("Plan Name");
      cs.newLineAtOffset(220, 0);
      cs.showText("Service");
      cs.newLineAtOffset(150, 0);
      cs.showText("Price");
      cs.endText();

      // // Draw table rows
      float rowY = tableTopY - 20; // adjust the row height and Y-coordinate
      // according to your requirement

      cs.beginText();
      cs.setFont(PDType1Font.TIMES_ROMAN, 14);
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
          rowY -= 20; // Adjust the rowY value for the next line
      }


      // Draw the price
      rowY += 20; // adjust the row height according to your requirement

      cs.beginText();
      cs.setFont(PDType1Font.TIMES_ROMAN, 14);
      // cs.newLineAtOffset(80, rowY);
      // cs.showText("Total");
      // cs.newLineAtOffset(150, 0);
      // cs.showText("");
      cs.newLineAtOffset(450, rowY);
      cs.showText(invoiceDTO.getPrice().toString());
      cs.endText();

      cs.setStrokingColor(Color.BLACK);
      cs.setLineWidth(1);
      cs.moveTo(420, rowY - 10); // Starting point of the line
      cs.lineTo(530, rowY - 10); // Ending point of the line
      cs.stroke();

      


      // Print "Discount"
      cs.beginText();
      cs.setFont(PDType1Font.TIMES_ROMAN, 14);
      cs.newLineAtOffset(280, rowY - 40);
      cs.showText("Discount");
      cs.endText();

      // Print the discount amount
      cs.beginText();
      cs.setFont(PDType1Font.TIMES_ROMAN, 14);
      cs.newLineAtOffset(450, rowY - 40);
      
      cs.showText(Double.toString(invoiceDTO.getPrice() - finalPrice ));
      cs.endText();

      // Print the discount amount
      cs.beginText();
      cs.setFont(PDType1Font.TIMES_ROMAN, 14);
      cs.newLineAtOffset(450, rowY - 40);
      double discount = invoiceDTO.getPrice() - finalPrice;
      cs.showText(Double.toString(discount));
      cs.endText();

      // Print the total amount
      cs.beginText();
      cs.setFont(PDType1Font.TIMES_ROMAN, 14);
      cs.newLineAtOffset(450, rowY - 60);
      cs.showText(Double.toString(finalPrice));
      cs.endText();







      // Close the content stream
      cs.close();
      String pdfFilePath = "/home/bibhu04/Microservices/onestop/onestop-invoice/invoice/customer_details.pdf";
      invc.save(pdfFilePath);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}

