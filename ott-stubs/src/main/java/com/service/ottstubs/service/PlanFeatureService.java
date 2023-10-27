package com.service.ottstubs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.service.ottstubs.entity.PlanFeature;
import com.service.ottstubs.repository.PlanFeatureRepository;

@Service
public class PlanFeatureService {

    private final PlanFeatureRepository planFeatureRepository;

    @Autowired
    public PlanFeatureService(PlanFeatureRepository planFeatureRepository) {
        this.planFeatureRepository = planFeatureRepository;
    }

    
    /** 
     * @return List<PlanFeature>
     */
    public List<PlanFeature> getAllPlanFeatures() {
        return planFeatureRepository.findAll();
    }

    public Optional<PlanFeature> getPlanFeatureById(Long id) {
        return planFeatureRepository.findById(id);
    }

    // Add other service methods as needed
}