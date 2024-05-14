package com.tutorial.springaudit.service;

import com.tutorial.springaudit.entity.RefreshToken;
import com.tutorial.springaudit.entity.User;
import com.tutorial.springaudit.entity.dto.UserDto;
import com.tutorial.springaudit.entity.request.AuthenticationRequest;
import com.tutorial.springaudit.entity.request.RegisterRequest;
import com.tutorial.springaudit.entity.request.TokenRequest;
import com.tutorial.springaudit.entity.response.AuthenticationResponse;
import com.tutorial.springaudit.mapper.UserMapper;
import com.tutorial.springaudit.repository.RefreshTokenRepository;
import com.tutorial.springaudit.repository.UserRepository;
import com.tutorial.springaudit.security.TokenService;
import com.tutorial.springaudit.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto register(RegisterRequest registerRequest) {
        User user = new User();
        user.setName(registerRequest.getName());
        user.setRole(registerRequest.getRole());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword()));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String jwtToken = tokenService.createToken(userPrincipal);
        String refreshToken = tokenService.createRefreshToken(userPrincipal);

        return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    @Override
    public AuthenticationResponse refresh(TokenRequest request) {

        String jwtToken = tokenService.generateJwtToken(request.getRefreshToken());
        return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(request.getRefreshToken()).build();
    }
}
