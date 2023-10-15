package com.service.onestopapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class OttsPerPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ottsPerPlanId;

    @ManyToOne
    @JoinColumn(name = "ott_id")
    private Ott ott;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;
}
