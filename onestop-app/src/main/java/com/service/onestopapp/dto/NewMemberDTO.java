package com.service.onestopapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class NewMemberDTO {
    private List<NameAndPhone> members;
}