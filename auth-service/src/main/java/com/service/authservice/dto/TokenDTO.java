package com.service.authservice.dto;

import lombok.Data;

@Data
public class TokenDTO {
    private String token;
    
    
    /** 
     * @return String
     */
    public String getToken() {
    	return token;
    }
}