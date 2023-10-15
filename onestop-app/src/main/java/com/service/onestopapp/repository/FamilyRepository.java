package com.service.onestopapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.onestopapp.entity.Family;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {
    Optional<Family> findByUserId(Long userId);
  
}