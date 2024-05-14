package com.tutorial.springaudit.controller;

import com.tutorial.springaudit.entity.request.AuthenticationRequest;
import com.tutorial.springaudit.entity.request.TokenRequest;
import com.tutorial.springaudit.entity.response.AuthenticationResponse;
import com.tutorial.springaudit.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/refresh")
    public AuthenticationResponse refreshToken(@RequestBody TokenRequest request) {
        return authenticationService.refresh(request);
    }
}
