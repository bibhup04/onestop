package com.service.ottstubs.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StubDTO {
    private List<Ott> otts;
    private UserDTO userDTO;
    private List<NameAndPhone> members;
    
}
