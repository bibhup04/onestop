package com.service.onestopinvoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.onestopinvoice.DTO.GenerateInvoiceDTO;
import com.service.onestopinvoice.DTO.InvoiceDTO;
import com.service.onestopinvoice.entity.Invoice;
import com.service.onestopinvoice.repository.InvoiceRepository;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public void addInvoice(InvoiceDTO invoiceDTO, GenerateInvoiceDTO generateInvoiceDTO,String pdfFilePath){
        
        Invoice invoice = new Invoice();
        
        invoice.setBillId(generateInvoiceDTO.getBillId());
        invoice.setUserId(generateInvoiceDTO.getUserId());
        invoice.setUserName(invoiceDTO.getNameAndPhones().get(0).getName());
        invoice.setEmailId(invoiceDTO.getEmailId());
        invoice.setPlanId(invoiceDTO.getPlanId());
        invoice.setFinalPrice(generateInvoiceDTO.getFinalPrice());
        invoice.setPlanDescription(invoiceDTO.getPlanDescription());
        invoice.setPath(pdfFilePath);

        invoiceRepository.save(invoice);
    }

    public List<Invoice> getAllInvoicesByUserId(Long userId) {
        //return invoiceRepository.findAllByUserId(userId);
        return invoiceRepository.findAllByUserIdOrderByIdDesc(userId);
    }

}