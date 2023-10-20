package com.service.onestopinvoice.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;



import org.springframework.stereotype.Service;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;


@Service
public class PdfService {
    
    public ByteArrayInputStream createPdf(){
        System.out.println("Create PDF started : ");
        String title = "Hello world";
        String content = "This is an invoice";

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Document document = new Document();

        PdfWriter.getInstance(document, out);

        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
        Paragraph titlePara = new Paragraph(title, titleFont);
        // titlePara.setAlignment(Element.ALIGN_CENTER);
        document.add(titlePara);

        Font paraFont = FontFactory.getFont(FontFactory.HELVETICA, 18);
        Paragraph paragraph = new Paragraph(content);
        paragraph.add(new Chunk("Wow this text is added after crating paragraph"));
        document.add(paragraph);

        document.close();

        return new ByteArrayInputStream(out.toByteArray());

    }
    
}
