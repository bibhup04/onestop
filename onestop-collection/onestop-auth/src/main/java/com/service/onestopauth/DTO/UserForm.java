package com.service.onestopauth.DTO;

import lombok.Data;

@Data
public class UserForm {
    private String username;
    private String password;
    private String passwordRepeat;
}
