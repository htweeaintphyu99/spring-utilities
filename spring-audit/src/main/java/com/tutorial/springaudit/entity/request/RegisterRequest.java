package com.tutorial.springaudit.entity.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterRequest {

    private String name;
    private String role;
    private String password;
}
