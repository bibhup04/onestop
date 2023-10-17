package com.service.onestopapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.onestopapp.dto.NameAndPhone;
import com.service.onestopapp.dto.NewMemberDTO;
import com.service.onestopapp.dto.PlanDTO;
import com.service.onestopapp.dto.PlanIdDTO;
import com.service.onestopapp.dto.SubscribeDTO;
import com.service.onestopapp.dto.UserDTO;
import com.service.onestopapp.entity.Family;
import com.service.onestopapp.entity.Plan;
import com.service.onestopapp.service.FamilyService;
import com.service.onestopapp.service.MemberService;
import com.service.onestopapp.service.PlanService;
import com.service.onestopapp.service.SubscribePlanService;
import com.service.onestopapp.service.UserService;
import com.service.onestopapp.util.JwtUtil;


import org.springframework.web.bind.annotation.RequestHeader;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/app")
//@CrossOrigin(origins = "http://localhost:4200")
public class AppController {

    private JwtUtil jwtUtil;

    private final PlanService planService;
    private final UserService userService;
    private final FamilyService familyService;
    private final MemberService memberService;
    private final SubscribePlanService subscribePlanService;

    @Autowired
    public AppController(PlanService planService, JwtUtil jwtUtil, UserService userService, FamilyService familyService, MemberService memberService, SubscribePlanService subscribePlanService) {
        this.planService = planService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.familyService = familyService;
        this.memberService = memberService;
        this.subscribePlanService = subscribePlanService;
    }


    @PostMapping("/plan/buy")
    public ResponseEntity<String> buyPlan(@RequestHeader("Authorization") String token,@RequestBody PlanIdDTO planIdDTO){
        
        UserDTO userDTO = userService.getUserDetails(token);
        Family family = familyService.getFamilyByUserId(userDTO.getId()).get();
        if(planService.getMemberCountByPlanId(planIdDTO.getPlanId())< memberService.getMemberCountByFamilyId(family)){
            return new ResponseEntity<>("Number of members is greater than the members count of plan", HttpStatus.BAD_REQUEST);
        }
        subscribePlanService.subscribePlan(planIdDTO.getPlanId(), userDTO, family);
        
        
        return new ResponseEntity<>( "plan buyed", HttpStatus.OK);
    }

    @GetMapping("/home")
    public List<PlanDTO> getPlans() {
        return planService.getAllPlansWithOtt();
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO> createFamilyDetails(@RequestHeader("Authorization") String token){
        UserDTO userDTO = userService.getUserDetails(token);
        Family family = familyService.checkAndCreateFamilyForUser(userDTO);
        return new ResponseEntity<>( userDTO, HttpStatus.OK);
    }


    @PostMapping("/addMember")
    public ResponseEntity<String> addFamilyMember(@RequestHeader("Authorization") String token,@RequestBody NewMemberDTO newMemberDTO){
        System.out.println("token isn " + token);
        UserDTO userDTO = userService.getUserDetails(token);
        System.out.println("New Member DTO: " + newMemberDTO);

        Family family = familyService.getFamilyByUserId(userDTO.getId()).get();
        System.out.println("familyId is " + family.getFamilyId());
         memberService.addMember(family, newMemberDTO);
        System.out.println("family members added");

        List<NameAndPhone> members = newMemberDTO.getMembers();
        for (NameAndPhone member : members) {
            System.out.println("Name: " + member.getName() + ", Phone Number: " + member.getPhoneNo());
        }

        return new ResponseEntity<>( "Family members added" + newMemberDTO.getMembers(), HttpStatus.OK);
    }

    @GetMapping("/getMember")
    public ResponseEntity<NewMemberDTO> getAllMember(@RequestHeader("Authorization") String token){
        UserDTO userDTO = userService.getUserDetails(token);
        Family family = familyService.getFamilyByUserId(userDTO.getId()).get();
        NewMemberDTO newMemberDTO = memberService.getMember(family);
        return new ResponseEntity<>( newMemberDTO, HttpStatus.OK);
    }




    @GetMapping("/home/user")
    public ResponseEntity<String> home(@RequestHeader("Authorization") String token) {
    if (token != null && token.startsWith("Bearer ")) {
        String jwtToken = token.substring(7); // Remove "Bearer " from the token string
        System.out.println(jwtToken);
        jwtUtil.decodeToken(jwtToken);
        String name = ""; // Here, you should extract the name from the decoded token.
        System.out.println("name is - " + name);
        return new ResponseEntity<>("You are in home, " + name, HttpStatus.OK);
    } else {
        // Handle the case when the token is not in the expected format
        return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
    }
}


    
}
