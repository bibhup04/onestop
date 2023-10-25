package com.service.ottstubs.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StubDTO {
    private PlanDTO planDTO;
    private UserDTO userDTO;
    private List<NameAndPhone> members;
    
}
