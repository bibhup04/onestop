package com.service.onestopapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.onestopapp.entity.Family;
import com.service.onestopapp.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    //Optional<Member> findByFamily(Family family);
     List<Member> findByFamily(Family family);
}