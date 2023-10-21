package com.service.onestopapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.onestopapp.dto.GenerateInvoiceDTO;
import com.service.onestopapp.dto.InvoiceDTO;
import com.service.onestopapp.dto.NewMemberDTO;
import com.service.onestopapp.entity.Family;
import com.service.onestopapp.entity.Ott;
import com.service.onestopapp.entity.Plan;

@Service
public class InvoiceService {

    @Autowired
    private FamilyService familyService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PlanService planService;

    @Autowired
    private OttsPerPlanService ottsPerPlanService;

    public InvoiceDTO generateData(GenerateInvoiceDTO generateInvoiceDTO){

        Plan plan = planService.getPlansById(generateInvoiceDTO.getPlanId()).get();
        List<Ott> otts = ottsPerPlanService.getOttsByPlan(plan);
        Family family = familyService.getFamilyByUserId(generateInvoiceDTO.getUserId()).get();
        NewMemberDTO newMemberDTO = memberService.getMember(family);

        InvoiceDTO invoiceDTO = new InvoiceDTO();

        invoiceDTO.setPlanId(plan.getPlanId());
        invoiceDTO.setPlanDescription(plan.getPlanDescription());
        invoiceDTO.setPrice(plan.getPrice());
        invoiceDTO.setOtts(otts);
        invoiceDTO.setNameAndPhones(newMemberDTO.getMembers());
        invoiceDTO.setEmailId(familyService.getEmailIdByUserId(generateInvoiceDTO.getUserId()));
        
        System.out.println("inside generate data method");;

        return invoiceDTO;
    }
    
}
