package com.service.onestopapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.onestopapp.dto.PlanDTO;
import com.service.onestopapp.entity.Ott;
import com.service.onestopapp.entity.Plan;
import com.service.onestopapp.repository.PlanRepository;

@Service
public class PlanService {

    private final PlanRepository plansRepository;
    private final OttsPerPlanService ottsPerPlanService;

    @Autowired
    public PlanService(PlanRepository plansRepository, OttsPerPlanService ottsPerPlanService) {
        this.plansRepository = plansRepository;
        this.ottsPerPlanService = ottsPerPlanService;
    }

    public List<Plan> getAllPlans() {
        return plansRepository.findAll();
    }

    public List<PlanDTO> getAllPlansWithOtt(){
        List<PlanDTO> planDTOs = new ArrayList<>();
        List<Plan> plans = plansRepository.findAll();
        for (Plan plan : plans) {
            List<Ott> otts = ottsPerPlanService.getOttsByPlan(plan);
            PlanDTO planDTO = new PlanDTO();
            planDTO.setPlanId(plan.getPlanId());
            planDTO.setBillingCycle(plan.getBillingCycle());
            planDTO.setMemberCount(plan.getMemberCount());
            planDTO.setPlanDescription(plan.getPlanDescription());
            planDTO.setOttCount(plan.getOttCount());
            planDTO.setStreams(plan.getStreams());
            planDTO.setPrice(plan.getPrice());
            planDTO.setDiscount(plan.getDiscount());
            planDTO.setFinalPrice(plan.getFinalPrice());
            planDTO.setOtts(otts);
            planDTOs.add(planDTO);
        }
        return planDTOs;
    }

    public Optional<Plan> getPlansById(Long id) {
        return plansRepository.findById(id);
    }

    public Plan savePlans(Plan plans) {
        return plansRepository.save(plans);
    }

    public void deletePlansById(Long id) {
        plansRepository.deleteById(id);
    }
}
