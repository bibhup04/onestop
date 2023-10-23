package com.service.onestopcollection.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.onestopcollection.entity.Collection;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
    // You can add custom query methods here if needed
}
