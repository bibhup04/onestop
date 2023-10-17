package com.service.ottstubs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.ottstubs.entity.PlanFeature;

@Repository
public interface PlanFeatureRepository extends JpaRepository<PlanFeature, Long> {
    // Add custom query methods if necessary
}