package com.service.authservice.controller;



import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import com.service.authservice.dto.AuthRequest;
import com.service.authservice.dto.TokenDTO;
import com.service.authservice.entity.UserCredential;
import com.service.authservice.service.AuthService;
import com.service.authservice.service.JwtService;
import com.service.authservice.service.UserCredentialService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserCredentialService userCredentialService;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user) {
        return service.saveUser(user);
    }

    @PostMapping("/token")
    public TokenDTO getToken(@RequestBody AuthRequest authRequest) {
         System.out.println(" inside this-------------");
         System.out.println("username - " + authRequest.getUsername() + " password - " + authRequest.getPassword());
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            System.out.println(" inside this-------------");
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(service.generateToken(authRequest.getUsername()));
            return tokenDTO;
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }

    @GetMapping("/userId")
    public ResponseEntity<UserCredential> home(@RequestHeader("Authorization") String token) {
    Map<String, Object> tokenInfo = new HashMap<>();
    if (token != null && token.startsWith("Bearer ")) {
        String jwtToken = token.substring(7); // Remove "Bearer " from the token string
        System.out.println(jwtToken);
        tokenInfo = jwtService.decodeToken(jwtToken);
        String name = (String) tokenInfo.get("sub");
        UserCredential user = userCredentialService.getUserCredentialByName(name).get();
        System.out.println("name is - " + name);
        return new ResponseEntity<>( user, HttpStatus.OK);
    } else {
        UserCredential userCredential = new UserCredential();
        return new ResponseEntity<>(userCredential, HttpStatus.BAD_REQUEST);
        }
    }

}

