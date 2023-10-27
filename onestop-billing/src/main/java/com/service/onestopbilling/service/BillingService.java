package com.service.onestopbilling.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    
    /** 
     * @return List<Billing>
     */
    public List<Billing> getAllBillings() {
        return billingRepository.findAll();
    }

    public Optional<Billing> findBillByUserIdAndPaymentStatusPending(long userId) {
        return billingRepository.findByUserIdAndPaymentStatus(userId, "PENDING");
    }

    public Billing updatePaymentStatusToPaid(Long billId) {
        Optional<Billing> billingOptional = billingRepository.findById(billId);
        if (billingOptional.isPresent()) {
            Billing billing = billingOptional.get();
            billing.setPaymentStatus("PAID");
            return billingRepository.save(billing);
        } else {
            throw new IllegalArgumentException("Bill with id " + billId + " not found.");
        }
    }

    public List<Long> findSubscriptionIdsByPaymentStatusPending() {
        List<Billing> pendingBills = billingRepository.findByPaymentStatus("PENDING");
        return pendingBills.stream()
                .map(Billing::getSubscriptionId)
                .collect(Collectors.toList());
    }

    public Billing getLastBillByUserId(Long userId) {
        return billingRepository.findTopByUserIdOrderByCreatedAtDesc(userId);
    }

    public ResponseEntity<String> endSubscriptionBill(Subscription subscription){
        
        Optional<Billing> existingBilling = findBillByUserIdAndPaymentStatusPending(subscription.getUserId());

        if(existingBilling.isPresent()){
            subscriptionService.deleteSubscriptionById(subscription.getSubscriptionId());
            return new ResponseEntity<>("Please pay the pending bill.", HttpStatus.OK);
        }

        List<GenerateInvoiceDTO> generateInvoiceDTOs = new ArrayList<>();
        Billing billing = new Billing();
        billing.setSubscriptionId(subscription.getSubscriptionId());
        billing.setAmount(subscription.getFinalPrice());
        billing.setPaymentStatus("PENDING");
        billing.setUserId(subscription.getUserId());
        billingRepository.save(billing);

        subscriptionService.deleteSubscriptionById(subscription.getSubscriptionId());

        GenerateInvoiceDTO generateInvoiceDTO = new GenerateInvoiceDTO();
        generateInvoiceDTO.setBillId(billing.getBillingId());
        generateInvoiceDTO.setPlanId(subscription.getPlanId());
        generateInvoiceDTO.setUserId(subscription.getUserId());
        generateInvoiceDTO.setFinalPrice(subscription.getFinalPrice());
        generateInvoiceDTOs.add(generateInvoiceDTO);
        
        return  invoiceService.createInvoice(generateInvoiceDTOs);
        
     }

    


    public List<Billing> createbills(){
        List<Subscription> subscriptions = subscriptionService.getActiveSubscriptions();
        List<GenerateInvoiceDTO> generateInvoiceDTOs = new ArrayList<>();
        for(Subscription sub : subscriptions){
            Billing billing = new Billing();
            billing.setAmount(sub.getFinalPrice());
            billing.setSubscriptionId(sub.getSubscriptionId());
            billing.setPaymentStatus("PENDING");
            billing.setUserId(sub.getUserId());
            billingRepository.save(billing);

            GenerateInvoiceDTO generateInvoiceDTO = new GenerateInvoiceDTO();
            generateInvoiceDTO.setBillId(billing.getBillingId());
            generateInvoiceDTO.setPlanId(sub.getPlanId());
            generateInvoiceDTO.setUserId(sub.getUserId());
            generateInvoiceDTO.setFinalPrice(sub.getFinalPrice());
            
            generateInvoiceDTOs.add(generateInvoiceDTO);
        }
        if(generateInvoiceDTOs.size() > 0){
            invoiceService.createInvoice(generateInvoiceDTOs);
        }
        
        return getAllBillings();
    }
}