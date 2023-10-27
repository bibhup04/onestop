package com.service.onestopapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.service.onestopapp.dto.NameAndPhone;
import com.service.onestopapp.dto.NewMemberDTO;
import com.service.onestopapp.dto.OrderResponseDTO;
import com.service.onestopapp.dto.Subscription;
import com.service.onestopapp.entity.Family;
import com.service.onestopapp.entity.Member;
import com.service.onestopapp.entity.Ott;
import com.service.onestopapp.entity.OttsPerPlan;
import com.service.onestopapp.entity.Plan;

public class OnestopAppEntityUnitTest {

     @Test
    public void testFamilyEntity() {
        Long expectedFamilyId = 1L;
        Long expectedUserId = 123L;
        String expectedEmailId = "example@example.com";

        Family family = new Family();
        family.setFamilyId(expectedFamilyId);
        family.setUserId(expectedUserId);
        family.setEmailId(expectedEmailId);

        assertEquals(expectedFamilyId, family.getFamilyId());
        assertEquals(expectedUserId, family.getUserId());
        assertEquals(expectedEmailId, family.getEmailId());
    }

    @Test
    public void testMemberEntity() {
        Long expectedMemberId = 1L;
        String expectedName = "John Doe";
        String expectedPhoneNo = "1234567890";
        Family expectedFamily = new Family(); // You can initialize a Family object with necessary data for testing

        Member member = new Member();
        member.setMemberId(expectedMemberId);
        member.setName(expectedName);
        member.setPhoneNo(expectedPhoneNo);
        member.setFamily(expectedFamily);

        assertEquals(expectedMemberId, member.getMemberId());
        assertEquals(expectedName, member.getName());
        assertEquals(expectedPhoneNo, member.getPhoneNo());
        assertEquals(expectedFamily, member.getFamily());
    }

     @Test
    public void testOttEntity() {
        Long expectedOttId = 1L;
        String expectedOttName = "Netflix";
        String expectedPlanTitle = "Premium Plan";

        Ott ott = new Ott();
        ott.setOttId(expectedOttId);
        ott.setOttName(expectedOttName);
        ott.setPlanTitle(expectedPlanTitle);

        assertEquals(expectedOttId, ott.getOttId());
        assertEquals(expectedOttName, ott.getOttName());
        assertEquals(expectedPlanTitle, ott.getPlanTitle());
    }

    @Test
    public void testOttsPerPlanEntity() {
        Long expectedOttsPerPlanId = 1L;
        Ott expectedOtt = new Ott(); 
        Plan expectedPlan = new Plan(); 

        OttsPerPlan ottsPerPlan = new OttsPerPlan();
        ottsPerPlan.setOttsPerPlanId(expectedOttsPerPlanId);
        ottsPerPlan.setOtt(expectedOtt);
        ottsPerPlan.setPlan(expectedPlan);

        assertEquals(expectedOttsPerPlanId, ottsPerPlan.getOttsPerPlanId());
        assertEquals(expectedOtt, ottsPerPlan.getOtt());
        assertEquals(expectedPlan, ottsPerPlan.getPlan());
    }

    @Test
    public void testPlanEntity() {
        Long expectedPlanId = 1L;
        Long expectedBillingCycle = 12L;
        Integer expectedMemberCount = 4;
        String expectedPlanDescription = "Sample plan description";
        Integer expectedOttCount = 3;
        Long expectedStreams = 2L;
        Double expectedPrice = 9.99;
        Double expectedDiscount = 1.99;
        Double expectedFinalPrice = expectedPrice - expectedDiscount;

        Plan plan = new Plan();
        plan.setPlanId(expectedPlanId);
        plan.setBillingCycle(expectedBillingCycle);
        plan.setMemberCount(expectedMemberCount);
        plan.setPlanDescription(expectedPlanDescription);
        plan.setOttCount(expectedOttCount);
        plan.setStreams(expectedStreams);
        plan.setPrice(expectedPrice);
        plan.setDiscount(expectedDiscount);
        plan.setFinalPrice(expectedFinalPrice);

        assertEquals(expectedPlanId, plan.getPlanId());
        assertEquals(expectedBillingCycle, plan.getBillingCycle());
        assertEquals(expectedMemberCount, plan.getMemberCount());
        assertEquals(expectedPlanDescription, plan.getPlanDescription());
        assertEquals(expectedOttCount, plan.getOttCount());
        assertEquals(expectedStreams, plan.getStreams());
        assertEquals(expectedPrice, plan.getPrice());
        assertEquals(expectedDiscount, plan.getDiscount());
        assertEquals(expectedFinalPrice, plan.getFinalPrice());
    }

    @Test
    public void testSubscription() {
        Long subscriptionId = 1L;
        Long familyId = 2L;
        long userId = 3L;
        long planId = 4L;
        Double finalPrice = 100.0;
        Date createdAt = new Date();
        Date endDate = new Date();
        String status = "active";
        boolean autoRenewal = true;

        Subscription subscription = new Subscription();
        subscription.setSubscriptionId(subscriptionId);
        subscription.setFamilyId(familyId);
        subscription.setUserId(userId);
        subscription.setPlanId(planId);
        subscription.setFinalPrice(finalPrice);
        subscription.setCreatedAt(createdAt);
        subscription.setEndDate(endDate);
        subscription.setStatus(status);
        subscription.setAutoRenewal(autoRenewal);

        assertEquals(subscriptionId, subscription.getSubscriptionId());
        assertEquals(familyId, subscription.getFamilyId());
        assertEquals(userId, subscription.getUserId());
        assertEquals(planId, subscription.getPlanId());
        assertEquals(finalPrice, subscription.getFinalPrice());
        assertEquals(createdAt, subscription.getCreatedAt());
        assertEquals(endDate, subscription.getEndDate());
        assertEquals(status, subscription.getStatus());
        assertEquals(autoRenewal, subscription.isAutoRenewal());
    }


     @Test
    public void testOrderResponseDTO() {
        String orderId = "12345";
        String name = "Sample Product";
        int qty = 2;
        double price = 19.99;
        Date orderDate = new Date();
        String status = "Pending";
        int estimateDeliveryWindow = 3;

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setOrderId(orderId);
        orderResponseDTO.setName(name);
        orderResponseDTO.setQty(qty);
        orderResponseDTO.setPrice(price);
        orderResponseDTO.setOrderDate(orderDate);
        orderResponseDTO.setStatus(status);
        orderResponseDTO.setEstimateDeliveryWindow(estimateDeliveryWindow);

        assertEquals(orderId, orderResponseDTO.getOrderId());
        assertEquals(name, orderResponseDTO.getName());
        assertEquals(qty, orderResponseDTO.getQty());
        assertEquals(price, orderResponseDTO.getPrice());
        assertEquals(orderDate, orderResponseDTO.getOrderDate());
        assertEquals(status, orderResponseDTO.getStatus());
        assertEquals(estimateDeliveryWindow, orderResponseDTO.getEstimateDeliveryWindow());
    }

    @Test
    public void testNameAndPhone() {
        String name = "John Doe";
        String phoneNo = "1234567890";

        NameAndPhone nameAndPhone = new NameAndPhone(name, phoneNo);

        assertEquals(name, nameAndPhone.getName());
        assertEquals(phoneNo, nameAndPhone.getPhoneNo());
    }

     @Test
    public void testAddMembers() {
        NewMemberDTO newMemberDTO = new NewMemberDTO();
        List<NameAndPhone> initialMembers = new ArrayList<>();
        initialMembers.add(new NameAndPhone("John Doe", "1234567890"));

        newMemberDTO.setMembers(initialMembers);

        List<NameAndPhone> newMembers = new ArrayList<>();
        newMembers.add(new NameAndPhone("Jane Smith", "0987654321"));
        newMembers.add(new NameAndPhone("Michael Johnson", "4567891230"));

        newMemberDTO.addMembers(newMembers);

        assertEquals(3, newMemberDTO.getMembers().size());
        assertEquals("John Doe", newMemberDTO.getMembers().get(0).getName());
        assertEquals("1234567890", newMemberDTO.getMembers().get(0).getPhoneNo());
        assertEquals("Jane Smith", newMemberDTO.getMembers().get(1).getName());
        assertEquals("0987654321", newMemberDTO.getMembers().get(1).getPhoneNo());
        assertEquals("Michael Johnson", newMemberDTO.getMembers().get(2).getName());
        assertEquals("4567891230", newMemberDTO.getMembers().get(2).getPhoneNo());
    }
    
}
