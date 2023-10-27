package com.service.onestopapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.service.onestopapp.dto.GenerateInvoiceDTO;
import com.service.onestopapp.dto.InvoiceDTO;
import com.service.onestopapp.dto.NameAndPhone;
import com.service.onestopapp.dto.NewMemberDTO;
import com.service.onestopapp.dto.PlanDTO;
import com.service.onestopapp.dto.PlanIdDTO;
import com.service.onestopapp.dto.Subscription;
import com.service.onestopapp.dto.UserDTO;
import com.service.onestopapp.entity.Family;

import com.service.onestopapp.service.FamilyService;
import com.service.onestopapp.service.InvoiceService;
import com.service.onestopapp.service.MemberService;
import com.service.onestopapp.service.PlanService;
import com.service.onestopapp.service.SubscribePlanService;
import com.service.onestopapp.service.UserService;
import com.service.onestopapp.util.JwtUtil;

import feign.FeignException;

import org.springframework.web.bind.annotation.RequestHeader;


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
    private InvoiceService invoiceService;

    @Autowired
    public AppController(PlanService planService, JwtUtil jwtUtil, UserService userService, FamilyService familyService, MemberService memberService, SubscribePlanService subscribePlanService) {
        this.planService = planService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.familyService = familyService;
        this.memberService = memberService;
        this.subscribePlanService = subscribePlanService;
    }


    
    /** 
     * @param token
     * @param planIdDTO
     * @return ResponseEntity<String>
     */
    @PostMapping("/plan/buy")
    public ResponseEntity<String> buyPlan(@RequestHeader("Authorization") String token, @RequestBody PlanIdDTO planIdDTO) {
        try {
            UserDTO userDTO = userService.getUserDetails(token);
            System.out.println("user name - " + userDTO.getName() + "plan Id - " + planIdDTO.getPlanId());
            Family family = familyService.getFamilyByUserId(userDTO.getId()).get();
            if (planService.getMemberCountByPlanId(planIdDTO.getPlanId()) < memberService.getMemberCountByFamilyId(family)) {
                return new ResponseEntity<>("Number of members is greater than the members count of the plan", HttpStatus.BAD_REQUEST);
            }
            return subscribePlanService.subscribePlan(planIdDTO.getPlanId(), userDTO, family);
        } catch (FeignException.BadRequest ex) {
            return new ResponseEntity<>(ex.contentUTF8(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/home")
    public List<PlanDTO> getPlans() {
        return planService.getAllPlansWithOtt();
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO> createFamilyDetails(@RequestHeader("Authorization") String token){
        UserDTO userDTO = userService.getUserDetails(token);
        familyService.checkAndCreateFamilyForUser(userDTO);
        return new ResponseEntity<>( userDTO, HttpStatus.OK);
    }


    @PostMapping("/addMember")
    public ResponseEntity<String> addFamilyMember(@RequestHeader("Authorization") String token,@RequestBody NewMemberDTO newMemberDTO){
        
        System.out.println("token isn " + token);
        UserDTO userDTO = userService.getUserDetails(token);
        System.out.println("New Member DTO: " + newMemberDTO.getMembers());
        ResponseEntity<Subscription> responseEntity  = subscribePlanService.getSubscriptionDetails(token);
        Subscription subscription = responseEntity.getBody();
        
        int newMembers = newMemberDTO.getMembers().size();
        Family family = familyService.getFamilyByUserId(userDTO.getId()).get();
        int existingMembers = memberService.getMemberCountByFamilyId(family);

        if(subscription != null){
          
          int MaxAcceptedMembers = planService.getMemberCountByPlanId(subscription.getPlanId());
          if(MaxAcceptedMembers < newMembers + existingMembers){
                return new ResponseEntity<>("Number of members is greater than the members count of the plan", HttpStatus.BAD_REQUEST);
          }

        } else if(newMembers + existingMembers >6){
            return new ResponseEntity<>("Number of members is greater than maximum limit", HttpStatus.BAD_REQUEST);
        }
        
     
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


    @PostMapping("/invoice-details")
    public ResponseEntity<List<InvoiceDTO>> generateInvoice(@RequestBody List<GenerateInvoiceDTO> generateInvoiceDTOs) {
        List<InvoiceDTO> invoiceDTOs = new ArrayList<>();
        for (GenerateInvoiceDTO generateInvoiceDTO : generateInvoiceDTOs) {
            System.out.println("Received GenerateInvoiceDTO:");
            System.out.println("Bill Id: " + generateInvoiceDTO.getBillId());
            System.out.println("User Id: " + generateInvoiceDTO.getUserId());
            System.out.println("Plan Id: " + generateInvoiceDTO.getPlanId());
            System.out.println("Final Price: " + generateInvoiceDTO.getFinalPrice());

            InvoiceDTO invoiceDTO = invoiceService.generateData(generateInvoiceDTO);
            invoiceDTOs.add(invoiceDTO);
        }
        return new ResponseEntity<>(invoiceDTOs, HttpStatus.OK);
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
