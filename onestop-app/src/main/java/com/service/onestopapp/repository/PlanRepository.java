package com.service.onestopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.onestopapp.entity.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    // You can add custom query methods here if needed
}
