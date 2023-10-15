package com.service.authservice.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.service.authservice.entity.UserCredential;

public interface UserCredentialRepository  extends CrudRepository<UserCredential,Long> {
    Optional<UserCredential> findByName(String username);

    
}
