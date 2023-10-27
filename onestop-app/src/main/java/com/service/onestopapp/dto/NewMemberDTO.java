package com.service.onestopapp.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewMemberDTO {
    private List<NameAndPhone> members;

    
    /** 
     * @param newMembers
     */
    public void addMembers(List<NameAndPhone> newMembers) {
        this.members.addAll(newMembers);
    }
}