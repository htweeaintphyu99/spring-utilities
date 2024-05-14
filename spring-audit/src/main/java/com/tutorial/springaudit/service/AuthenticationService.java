package com.tutorial.springaudit.service;

import com.tutorial.springaudit.entity.dto.UserDto;
import com.tutorial.springaudit.entity.request.AuthenticationRequest;
import com.tutorial.springaudit.entity.request.RegisterRequest;
import com.tutorial.springaudit.entity.response.AuthenticationResponse;

public interface AuthenticationService {

    UserDto register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
