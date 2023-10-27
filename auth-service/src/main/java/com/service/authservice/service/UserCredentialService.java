package com.service.authservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.authservice.entity.UserCredential;
import com.service.authservice.repository.UserCredentialRepository;

@Service
public class UserCredentialService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    
    /** 
     * @return List<UserCredential>
     */
    public List<UserCredential> getAllUserCredentials() {
    return StreamSupport.stream(userCredentialRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
    }


    public Optional<UserCredential> getUserCredentialById(Long id) {
        return userCredentialRepository.findById(id);
    }

    public UserCredential saveUserCredential(UserCredential userCredential) {
        return userCredentialRepository.save(userCredential);
    }

    public void deleteUserCredential(Long id) {
        userCredentialRepository.deleteById(id);
    }

    
    public Optional<UserCredential> getUserCredentialByName(String username) {
        return userCredentialRepository.findByName(username);
    }
}