package com.service.onestopapp.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNo;
    private String password;
    private String role;
    private String status;

}
