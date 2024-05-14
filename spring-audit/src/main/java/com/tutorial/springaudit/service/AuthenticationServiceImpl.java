package com.tutorial.springaudit.service;

import com.tutorial.springaudit.entity.User;
import com.tutorial.springaudit.entity.dto.UserDto;
import com.tutorial.springaudit.entity.request.AuthenticationRequest;
import com.tutorial.springaudit.entity.request.RegisterRequest;
import com.tutorial.springaudit.entity.response.AuthenticationResponse;
import com.tutorial.springaudit.mapper.UserMapper;
import com.tutorial.springaudit.repository.UserRepository;
import com.tutorial.springaudit.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
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
        String jwtToken = jwtService.createToken(authentication);
        return AuthenticationResponse.builder().accessToken(jwtToken).build();

    }
}
