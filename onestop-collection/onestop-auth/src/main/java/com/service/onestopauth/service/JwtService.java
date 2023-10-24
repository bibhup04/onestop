package com.service.onestopauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;



@Service
public class JwtService {

    @Autowired
    private JwtDecoder jwtDecoder;

    public Jwt decodeToken(String token) {
        try {
            return jwtDecoder.decode(token);
        } catch (JwtException e) {
            // Handle the exception or rethrow it
            throw new RuntimeException("Failed to decode JWT: " + e.getMessage());
        }
    }

//     public boolean validateToken(String token) {
//         try {
//             Jwt jwt = jwtDecoder.decode(token);
//             // You can add custom validation logic here if needed
//             return jwt != null;
//         } catch (JwtException e) {
//             return false;
//         }
//     }
    
}
