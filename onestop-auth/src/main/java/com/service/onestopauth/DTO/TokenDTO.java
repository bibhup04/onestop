package com.service.onestopauth.DTO;

import lombok.Data;

@Data
public class TokenDTO {
    private String token;
    
    public String getToken() {
    	return token;
    }
}