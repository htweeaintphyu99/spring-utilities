package com.tutorial.querydsl.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {

    private String name;
    private String role;
    private String password;
}

