package com.service.onestopauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.onestopauth.DTO.TokenDTO;
import com.service.onestopauth.service.JwtService;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    @Autowired
    private JwtService jwtService;

    TokenDTO tokenDTO;

    @PostMapping("/validate")
    public boolean validateToken(@RequestBody String token) {
        return true;
    }

    @PostMapping("/decode")
    public Jwt decodeToken(@RequestBody String token) {
        return jwtService.decodeToken(token);
    }
}
