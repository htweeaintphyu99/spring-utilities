package com.example.multitenantusers.service;

import com.example.multitenantusers.model.DatabaseConfig;
import com.example.multitenantusers.repository.DatabaseConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DatabaseConfigService {

    private final DatabaseConfigRepository databaseConfigRepository;

    public void save(DatabaseConfig databaseConfig) {
        databaseConfigRepository.save(databaseConfig);
    }
}
