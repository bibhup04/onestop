package com.service.onestopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.onestopapp.entity.Ott;

@Repository
public interface OttRepository extends JpaRepository<Ott, Long> {
    // You can add custom query methods here if needed
}