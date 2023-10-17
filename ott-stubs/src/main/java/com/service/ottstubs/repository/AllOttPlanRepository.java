package com.service.ottstubs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.ottstubs.entity.AllOttPlan;

@Repository
public interface AllOttPlanRepository extends JpaRepository<AllOttPlan, Long> {
    // Add custom query methods if necessary
}