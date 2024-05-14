package com.tutorial.springaudit.entity.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {

    private String name;
    private String password;
}
