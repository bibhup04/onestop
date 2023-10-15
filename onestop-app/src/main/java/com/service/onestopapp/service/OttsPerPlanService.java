package com.service.onestopapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.onestopapp.entity.Ott;
import com.service.onestopapp.entity.OttsPerPlan;
import com.service.onestopapp.entity.Plan;
import com.service.onestopapp.repository.OttsPerPlanRepository;

@Service
public class OttsPerPlanService {

    private final OttsPerPlanRepository ottsPerPlanRepository;

    @Autowired
    public OttsPerPlanService(OttsPerPlanRepository ottsPerPlanRepository) {
        this.ottsPerPlanRepository = ottsPerPlanRepository;
    }

    public List<OttsPerPlan> getAllOttsPerPlan() {
        return ottsPerPlanRepository.findAll();
    }

    public Optional<OttsPerPlan> getOttsPerPlanById(Long id) {
        return ottsPerPlanRepository.findById(id);
    }

    public OttsPerPlan saveOttsPerPlan(OttsPerPlan ottsPerPlan) {
        return ottsPerPlanRepository.save(ottsPerPlan);
    }

    public void deleteOttsPerPlanById(Long id) {
        ottsPerPlanRepository.deleteById(id);
    }

    public List<Ott> getOttsByPlan(Plan plan) {
        List<Ott> otts = new ArrayList<>();
        List<OttsPerPlan> ottsPerPlans = ottsPerPlanRepository.findOttsByPlan(plan);
        for(OttsPerPlan ottsPP : ottsPerPlans){
            otts.add(ottsPP.getOtt());
        }
        return otts;
    }
}
