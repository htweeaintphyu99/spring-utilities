package com.tutorial.criteria.entity.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class AuthenticationResponse {

    private String accessToken;
}
