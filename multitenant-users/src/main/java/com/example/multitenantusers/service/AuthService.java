package com.example.multitenantusers.service;


import com.example.multitenantusers.model.DatabaseConfig;
import com.example.multitenantusers.model.User;
import com.example.multitenantusers.model.request.RegisterRequest;
import com.example.multitenantusers.repository.DatabaseConfigRepository;
import com.example.multitenantusers.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final DatabaseConfigRepository databaseConfigRepository;

    public void registerUser(RegisterRequest registerRequest) {

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        userRepository.save(user);

        DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseConfig.setUsername(registerRequest.getUsername());
        databaseConfig.setPassword(registerRequest.getPassword());
        databaseConfig.setUrl(registerRequest.getUrl());
        databaseConfig.setDriverClassName(registerRequest.getDriverClassName());
        databaseConfigRepository.save(databaseConfig);


    }
}
