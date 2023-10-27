package com.service.onestopapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Ott {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ottId;

    private String ottName;

    private String planTitle;
}
