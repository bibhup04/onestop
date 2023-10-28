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

import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/app")
//@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "App Controller", description = "allow user to login and register")
public class AppController {

    private final PlanService planService;
    private final UserService userService;
    private final FamilyService familyService;
    private final MemberService memberService;
    private final SubscribePlanService subscribePlanService;
    
    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    public AppController(PlanService planService,  UserService userService, FamilyService familyService, MemberService memberService, SubscribePlanService subscribePlanService) {
        this.planService = planService;
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
    @Operation(summary = "Buy a Plan", description = "Allows users to buy a plan (Identifies user from token)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Plan Subscribed successfully"),
        @ApiResponse(responseCode = "401", description = "Invalid access(wrong token)",
                content = @Content),
        @ApiResponse(responseCode = "400", description = "When no. of members in one account is greater than the maximum limit of plan.",
                content = @Content),
        @ApiResponse(responseCode = "400", description = "Bad Request",
                content = @Content(mediaType = "text/plain"))
        
    })
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


    @Operation(summary = "Home Page", description = "This endpoint don't dont require authentication. It displays the homepage directly.")
    @GetMapping("/home")
    public List<PlanDTO> getPlans() {
        return planService.getAllPlansWithOtt();
    }

    @Operation(summary = "User Details", description = "Fetch token from header, send token to Auth-service and get user details and creates a entry in family table if there is not netry in for that user.")
    @GetMapping("/user")
    public ResponseEntity<UserDTO> createFamilyDetails(@RequestHeader("Authorization") String token){
        UserDTO userDTO = userService.getUserDetails(token);
        familyService.checkAndCreateFamilyForUser(userDTO);
        return new ResponseEntity<>( userDTO, HttpStatus.OK);
    }


    @Operation(summary = "Add Members", description = "Identify the user from token and add members to the family account of the user.")
    @PostMapping("/addMember")
    public ResponseEntity<String> addFamilyMember(@RequestHeader("Authorization") String token,@RequestBody NewMemberDTO newMemberDTO){

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
        
        memberService.addMember(family, newMemberDTO);

        return new ResponseEntity<>( "Family members added" + newMemberDTO.getMembers(), HttpStatus.OK);
    }

    @PostMapping("/delete/member")
    public ResponseEntity<String> deleteFamilyMember(@RequestHeader("Authorization") String token, @RequestBody NameAndPhone nameAndPhone){
        UserDTO userDTO = userService.getUserDetails(token);
        ResponseEntity<Subscription> responseEntity  = subscribePlanService.getSubscriptionDetails(token);
        Subscription subscription = responseEntity.getBody();
        if(subscription != null){
            return new ResponseEntity<>("You can not remove members while subscribed to a plan.", HttpStatus.BAD_REQUEST);
        }
        
        Family family = familyService.getFamilyByUserId(userDTO.getId()).get();
        memberService.deleteMembersByFamilyAndName(family, nameAndPhone.getName());
        return new ResponseEntity<>( "Family members deleted successfully", HttpStatus.OK);
    }

    @Operation(summary = "Display Members", description = "Identify the user from token and add members to the family account of the user.")
    @GetMapping("/getMember")
    public ResponseEntity<NewMemberDTO> getAllMember(@RequestHeader("Authorization") String token){
        UserDTO userDTO = userService.getUserDetails(token);
        Family family = familyService.getFamilyByUserId(userDTO.getId()).get();
        NewMemberDTO newMemberDTO = memberService.getMember(family);
        return new ResponseEntity<>( newMemberDTO, HttpStatus.OK);
    }


    @Operation(summary = "Invoice Details", description = "Recieves a list of generateInvoiceDTOs and returns a list of InvoiceDTO with all the details to generate Invoice.")
    @PostMapping("/invoice-details")
    public ResponseEntity<List<InvoiceDTO>> generateInvoice(@RequestBody List<GenerateInvoiceDTO> generateInvoiceDTOs) {
        List<InvoiceDTO> invoiceDTOs = new ArrayList<>();
        for (GenerateInvoiceDTO generateInvoiceDTO : generateInvoiceDTOs) {
             InvoiceDTO invoiceDTO = invoiceService.generateData(generateInvoiceDTO);
            invoiceDTOs.add(invoiceDTO);
        }
        return new ResponseEntity<>(invoiceDTOs, HttpStatus.OK);
    }
    
}
