package com.service.ottstubs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class PlanFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plansFeaturesId;

    @ManyToOne
    @JoinColumn(name = "all_ott_plans_id")
    private AllOttPlan allOttPlan;

    @ManyToOne
    @JoinColumn(name = "feature_id")
    private Feature feature;

}