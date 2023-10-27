package com.service.onestopapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.onestopapp.dto.NameAndPhone;

import com.service.onestopapp.dto.StubDTO;
import com.service.onestopapp.dto.UserDTO;
import com.service.onestopapp.entity.Family;

import com.service.onestopapp.entity.Ott;
import com.service.onestopapp.entity.Plan;
import com.service.onestopapp.feignclint.OttStubServiceClient;

@Service
public class OttStubService {

    private final OttStubServiceClient ottStubServiceClient;
    
    @Autowired
    private OttsPerPlanService ottsPerPlanService;

    @Autowired
    private MemberService memberService;

    public OttStubService(OttStubServiceClient ottStubServiceClient) {
        this.ottStubServiceClient = ottStubServiceClient;
    }

    
    /** 
     * @param plan
     * @param userDTO
     * @param family
     * @return String
     */
    public String sendStubDTO(Plan plan, UserDTO userDTO, Family family) {
        List<Ott> ott = ottsPerPlanService.getOttsByPlan(plan);
        List<NameAndPhone> nameAndPhones = memberService.getMember(family).getMembers();

        StubDTO stubDTO = new StubDTO();
        stubDTO.setOtts(ott);
        stubDTO.setMembers(nameAndPhones);
        stubDTO.setUserDTO(userDTO);

        return ottStubServiceClient.receiveStubDTO(stubDTO);
    }
}
