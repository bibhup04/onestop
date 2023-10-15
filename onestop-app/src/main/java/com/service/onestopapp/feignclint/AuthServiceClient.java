package com.service.onestopapp.feignclint;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.service.onestopapp.dto.UserDTO;

@FeignClient(name = "AUTH-SERVICE", url = "http://localhost:8080/auth")
public interface AuthServiceClient {

    @GetMapping("/userId")
    ResponseEntity<UserDTO> getUserDetails(@RequestHeader("Authorization") String token);
}