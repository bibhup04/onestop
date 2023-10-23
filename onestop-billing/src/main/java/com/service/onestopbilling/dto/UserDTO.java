package com.service.onestopbilling.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNo;
    private String password;
    private String role;
    private String status;

}

