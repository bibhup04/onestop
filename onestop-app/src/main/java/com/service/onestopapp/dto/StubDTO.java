package com.service.onestopapp.dto;

import java.util.List;

import com.service.onestopapp.entity.Ott;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StubDTO {
    private List<Ott> otts;
    private UserDTO userDTO;
    private List<NameAndPhone> members;
    
}
