package com.service.onestopapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.service.onestopapp.dto.UserDTO;
import com.service.onestopapp.feignclint.AuthServiceClient;

@Service
public class UserService {

    @Autowired
    private AuthServiceClient authServiceClient;

    public UserDTO getUserDetails(String token) {
        ResponseEntity<UserDTO> responseEntity = authServiceClient.getUserDetails(token);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            // Handle the error case here
            return null;
        }
    }
}
