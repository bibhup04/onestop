package com.service.onestopbilling;


import org.springframework.data.jpa.repository.JpaRepository;

import com.service.onestopbilling.entity.Subscription;

public interface TestH2Repository extends JpaRepository<Subscription,Long> {
}
