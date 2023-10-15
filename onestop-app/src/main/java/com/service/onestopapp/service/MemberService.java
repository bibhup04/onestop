package com.service.onestopapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.onestopapp.dto.NameAndPhone;
import com.service.onestopapp.dto.NewMemberDTO;
import com.service.onestopapp.dto.UserDTO;
import com.service.onestopapp.entity.Family;
import com.service.onestopapp.entity.Member;
import com.service.onestopapp.repository.MemberRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public void deleteMemberById(Long id) {
        memberRepository.deleteById(id);
    }

    public Member checkAndCreateMemberForFamily(UserDTO userDTO, Family family) {
        Optional<Member> existingMember = memberRepository.findByFamily(family);
        if (existingMember.isPresent()) {
            return existingMember.get();
        } else {
            Member newMember = new Member();
            newMember.setName(userDTO.getName());
            newMember.setPhoneNo(userDTO.getPhoneNo());
            newMember.setFamily(family);
            return memberRepository.save(newMember);
        }
    }

    public void addMember(Family family,NewMemberDTO newMemberDTO){
        for (NameAndPhone member : newMemberDTO.getMembers()) {
            Member nMember = new Member();
            nMember.setName(member.getName());
            nMember.setPhoneNo(member.getPhoneNo());
            nMember.setFamily(family);
            memberRepository.save(nMember);
            System.out.println("inside member class with " + nMember.getName());
        }
    }
}