package com.service.onestopcollection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.service.onestopcollection.DTO.UserDTO;
import com.service.onestopcollection.feignclient.AuthServiceClient;



@Service
public class UserService {

    @Autowired
    private AuthServiceClient authServiceClient;

    
    /** 
     * @param token
     * @return UserDTO
     */
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