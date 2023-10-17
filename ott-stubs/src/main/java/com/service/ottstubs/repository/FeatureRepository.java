package com.service.ottstubs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.ottstubs.entity.Feature;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
    // Add custom query methods if necessary
}