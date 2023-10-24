package com.service.onestopinvoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.onestopinvoice.entity.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findAllByUserId(Long userId);
    
    List<Invoice> findAllByUserIdOrderByIdDesc(Long userId);
   
}
