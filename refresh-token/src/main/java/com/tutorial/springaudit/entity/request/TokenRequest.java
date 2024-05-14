package com.tutorial.springaudit.entity.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class TokenRequest {

    @NotBlank
    private String refreshToken;
}
