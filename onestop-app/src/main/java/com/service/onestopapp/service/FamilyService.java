package com.service.onestopapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.onestopapp.dto.UserDTO;
import com.service.onestopapp.entity.Family;
import com.service.onestopapp.repository.FamilyRepository;

@Service
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final MemberService memberService;

    @Autowired
    public FamilyService(FamilyRepository familyRepository, MemberService memberService) {
        this.familyRepository = familyRepository;
        this.memberService = memberService;
    }

    
    /** 
     * @return List<Family>
     */
    public List<Family> getAllFamilies() {
        return familyRepository.findAll();
    }

    public Optional<Family> getFamilyById(Long id) {
        return familyRepository.findById(id);
    }

    public Family saveFamily(Family family) {
        return familyRepository.save(family);
    }

    public void deleteFamilyById(Long id) {
        familyRepository.deleteById(id);
    }

    public Optional<Family> getFamilyByUserId(Long userId) {
        return familyRepository.findByUserId(userId);
    }

    public Family checkAndCreateFamilyForUser(UserDTO userDTO) {
        Optional<Family> existingFamily = familyRepository.findByUserId(userDTO.getId());
        if (existingFamily.isPresent()) {
            return existingFamily.get();
        } else {
            Family newFamily = new Family();
            newFamily.setUserId(userDTO.getId());
            newFamily.setEmailId(userDTO.getEmail());
            Family createdFamily = familyRepository.save(newFamily);
            memberService.checkAndCreateMemberForFamily(userDTO, createdFamily);
            return createdFamily;
        }
    }

    public String getEmailIdByUserId(Long userId) {
        Optional<Family> familyOptional = familyRepository.findByUserId(userId);
        return familyOptional.map(Family::getEmailId).orElse(null);
    }
}
