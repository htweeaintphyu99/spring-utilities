package com.tutorial.springaudit.service;

import com.tutorial.springaudit.entity.dto.UserDto;
import com.tutorial.springaudit.entity.request.AuthenticationRequest;
import com.tutorial.springaudit.entity.request.RegisterRequest;
import com.tutorial.springaudit.entity.request.TokenRequest;
import com.tutorial.springaudit.entity.response.AuthenticationResponse;
import com.tutorial.springaudit.security.UserPrincipal;

public interface AuthenticationService {

    UserDto register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse refresh(TokenRequest request);
}
