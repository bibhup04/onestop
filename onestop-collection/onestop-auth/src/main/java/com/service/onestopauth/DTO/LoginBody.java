package com.service.onestopauth.DTO;

import lombok.Data;

@Data
public class LoginBody {

    private String username;

    private String password;
    
    public String getUsername() {
    	return username;
    }
    
    public String getPassword() {
    	return password;
    }
}