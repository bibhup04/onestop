package com.service.onestopbilling.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.onestopbilling.dto.GenerateInvoiceDTO;
import com.service.onestopbilling.entity.Billing;
import com.service.onestopbilling.entity.Subscription;
import com.service.onestopbilling.repository.BillingRepository;

@Service
public class BillingService {

    private final BillingRepository billingRepository;
    private final SubscriptionService subscriptionService;
    private final InvoiceService invoiceService;

    @Autowired
    public BillingService(InvoiceService invoiceService, BillingRepository billingRepository, SubscriptionService subscriptionService) {
        this.billingRepository = billingRepository;
        this.subscriptionService = subscriptionService;
        this.invoiceService = invoiceService;
    }

    public List<Billing> getAllBillings() {
        return billingRepository.findAll();
    }

    public Billing findBillByUserIdAndPaymentStatusPending(long userId) {
        return billingRepository.findByUserIdAndPaymentStatus(userId, "Pending");
    }

    public Billing updatePaymentStatusToPaid(Long billId) {
        Optional<Billing> billingOptional = billingRepository.findById(billId);
        if (billingOptional.isPresent()) {
            Billing billing = billingOptional.get();
            billing.setPaymentStatus("Paid");
            return billingRepository.save(billing);
        } else {
            throw new IllegalArgumentException("Bill with id " + billId + " not found.");
        }
    }


    public List<Billing> createbills(){
        List<Subscription> subscriptions = subscriptionService.getActiveSubscriptions();
        List<GenerateInvoiceDTO> generateInvoiceDTOs = new ArrayList<>();
        for(Subscription sub : subscriptions){
            Billing billing = new Billing();
            billing.setAmount(sub.getFinalPrice());
            billing.setSubscriptionId(sub.getSubscriptionId());
            billing.setPaymentStatus("Pending");
            billing.setUserId(sub.getUserId());
            billingRepository.save(billing);

            GenerateInvoiceDTO generateInvoiceDTO = new GenerateInvoiceDTO();
            generateInvoiceDTO.setBillId(billing.getBillingId());
            generateInvoiceDTO.setPlanId(sub.getPlanId());
            generateInvoiceDTO.setUserId(sub.getUserId());
            generateInvoiceDTO.setFinalPrice(sub.getFinalPrice());
            
            generateInvoiceDTOs.add(generateInvoiceDTO);
        }
        invoiceService.createInvoice(generateInvoiceDTOs);
        return getAllBillings();
    }
}