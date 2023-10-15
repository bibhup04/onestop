package com.service.onestopapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.onestopapp.entity.Ott;
import com.service.onestopapp.entity.OttsPerPlan;
import com.service.onestopapp.entity.Plan;

@Repository
public interface OttsPerPlanRepository extends JpaRepository<OttsPerPlan, Long> {
    
    List<OttsPerPlan> findOttsByPlan(Plan plan);
}
