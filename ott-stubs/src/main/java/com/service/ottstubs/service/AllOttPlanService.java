package com.service.ottstubs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.ottstubs.entity.AllOttPlan;
import com.service.ottstubs.repository.AllOttPlanRepository;

@Service
public class AllOttPlanService {

    private final AllOttPlanRepository allOttPlanRepository;

    @Autowired
    public AllOttPlanService(AllOttPlanRepository allOttPlanRepository) {
        this.allOttPlanRepository = allOttPlanRepository;
    }

    public List<AllOttPlan> getAllOttPlans() {
        return allOttPlanRepository.findAll();
    }

    public Optional<AllOttPlan> getOttPlanById(Long id) {
        return allOttPlanRepository.findById(id);
    }

    // Add other service methods as needed
}
