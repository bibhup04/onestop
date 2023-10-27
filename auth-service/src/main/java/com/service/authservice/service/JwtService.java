package com.service.authservice.service;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.service.authservice.repository.UserCredentialRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;


    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";


    
    /** 
     * @param token
     */
    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }


    public String generateToken(String userName) {
        // Fetch user roles from the database based on the userName
        String userRole = userCredentialRepository.findByName(userName).get().getRole();
        System.out.println("######### user role is " + userRole);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userRole); // Add role to the claims

        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }
    

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

      public Map<String, Object> decodeToken(String token) {
        Map<String, Object> tokenInfo = new HashMap<>();
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();

            
            tokenInfo.put("role", claims.get("role", String.class));
            tokenInfo.put("sub", claims.get("sub", String.class));
            tokenInfo.put("iat", claims.get("iat", Date.class));
            tokenInfo.put("exp", claims.get("exp", Date.class));

            System.out.println("Decoded Token Information:");
            tokenInfo.forEach((key, value) -> System.out.println(key + ": " + value));
            
        } catch (Exception e) {
            System.out.println("Error decoding the token: " + e.getMessage());
        }
        return tokenInfo;
    }
}
