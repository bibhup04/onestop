package com.service.onestopbilling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.service.onestopbilling.dto.UserDTO;
import com.service.onestopbilling.feignclient.AuthServiceClient;

@Service
public class UserService {

    @Autowired
    private AuthServiceClient authServiceClient;

    public UserDTO getUserDetails(String token) {
        ResponseEntity<UserDTO> responseEntity = authServiceClient.getUserDetails(token);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            return null;
        }
    }
}