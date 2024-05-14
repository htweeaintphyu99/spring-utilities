package com.tutorial.springaudit.entity.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class AuthenticationResponse {

    private String accessToken;
    private String refreshToken;
}
