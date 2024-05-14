package com.example.multitenantusers.controller;

import com.example.multitenantusers.model.User;
import com.example.multitenantusers.model.request.LoginRequest;
import com.example.multitenantusers.model.request.RegisterRequest;
import com.example.multitenantusers.model.response.JwtAuthenticationResponse;
import com.example.multitenantusers.security.JwtTokenProvider;
import com.example.multitenantusers.service.AuthService;
import com.example.multitenantusers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Date expiredAt = new Date((new Date()).getTime() + 86400 * 1000);

        String jwtToken;
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            jwtToken = jwtTokenProvider.createJwtToken(authentication);
        } catch (Exception e) {
            throw new BadCredentialsException("Username or password is incorrect!");
        }

        return new ResponseEntity<>(new JwtAuthenticationResponse(jwtToken, expiredAt.toInstant().toString()), HttpStatus.OK);
    }

    @PreAuthorize("ROLE_ADMIN")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        Optional<User> user = userService.findUser(registerRequest.getUsername());
        if(user.isPresent()) {
            return new ResponseEntity<>("User already exists!", HttpStatus.BAD_REQUEST);
        }
        authService.registerUser(registerRequest);
        return new ResponseEntity<>("Registration is successful.", HttpStatus.CREATED);
    }
}
