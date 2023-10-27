package com.service.ottstubs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.service.ottstubs.DTO.NameAndPhone;
import com.service.ottstubs.DTO.Ott;
import com.service.ottstubs.DTO.UserDTO;
import com.service.ottstubs.entity.AllOttPlan;
import com.service.ottstubs.entity.Feature;
import com.service.ottstubs.entity.PlanFeature;

public class OttStubsUnitTest {
    
    @Mock
    AllOttPlan allOttPlan;

    @Mock
    Feature feature;

    @Test
    public void testPlanFeature() {
        Long plansFeaturesId = 1L;

        PlanFeature planFeature = new PlanFeature();
        planFeature.setPlansFeaturesId(plansFeaturesId);
        planFeature.setAllOttPlan(allOttPlan);
        planFeature.setFeature(feature);

        assertEquals(plansFeaturesId, planFeature.getPlansFeaturesId());
        assertEquals(allOttPlan, planFeature.getAllOttPlan());
        assertEquals(feature, planFeature.getFeature());
    }

    @Test
    public void testFeature() {
        Long featureId = 1L;
        String featureDescription = "Sample feature description";

        Feature feature = new Feature();
        feature.setFeatureId(featureId);
        feature.setFeatureDescription(featureDescription);

        assertEquals(featureId, feature.getFeatureId());
        assertEquals(featureDescription, feature.getFeatureDescription());
    }

    @Test
    public void testAllOttPlan() {
        Long allOttPlansId = 1L;
        String ottName = "Netflix";
        String planTitle = "Premium";
        Double planPrice = 15.99;
        Long planDuration = 30L;

        AllOttPlan allOttPlan = new AllOttPlan();
        allOttPlan.setAllOttPlansId(allOttPlansId);
        allOttPlan.setOttName(ottName);
        allOttPlan.setPlanTitle(planTitle);
        allOttPlan.setPlanPrice(planPrice);
        allOttPlan.setPlanDuration(planDuration);

        assertEquals(allOttPlansId, allOttPlan.getAllOttPlansId());
        assertEquals(ottName, allOttPlan.getOttName());
        assertEquals(planTitle, allOttPlan.getPlanTitle());
        assertEquals(planPrice, allOttPlan.getPlanPrice());
        assertEquals(planDuration, allOttPlan.getPlanDuration());
    }

     @Test
    public void testUserDTO() {
        Long id = 1L;
        String name = "John Doe";
        String email = "johndoe@example.com";
        String phoneNo = "1234567890";
        String password = "password123";
        String role = "user";
        String status = "active";

        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setName(name);
        userDTO.setEmail(email);
        userDTO.setPhoneNo(phoneNo);
        userDTO.setPassword(password);
        userDTO.setRole(role);
        userDTO.setStatus(status);

        assertEquals(id, userDTO.getId());
        assertEquals(name, userDTO.getName());
        assertEquals(email, userDTO.getEmail());
        assertEquals(phoneNo, userDTO.getPhoneNo());
        assertEquals(password, userDTO.getPassword());
        assertEquals(role, userDTO.getRole());
        assertEquals(status, userDTO.getStatus());
    }

     @Test
    public void testOtt() {
        Long ottId = 1L;
        String ottName = "Netflix";
        String planTitle = "Premium";

        Ott ott = new Ott();
        ott.setOttId(ottId);
        ott.setOttName(ottName);
        ott.setPlanTitle(planTitle);

        assertEquals(ottId, ott.getOttId());
        assertEquals(ottName, ott.getOttName());
        assertEquals(planTitle, ott.getPlanTitle());
    }

     @Test
    public void testNameAndPhone() {
        String name = "John Doe";
        String phoneNo = "1234567890";

        NameAndPhone nameAndPhone = new NameAndPhone();
        nameAndPhone.setName(name);
        nameAndPhone.setPhoneNo(phoneNo);

        assertEquals(name, nameAndPhone.getName());
        assertEquals(phoneNo, nameAndPhone.getPhoneNo());
    }
}
