package com.example.multitenantusers.repository;

import com.example.multitenantusers.model.DatabaseConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseConfigRepository extends JpaRepository<DatabaseConfig, Long> {
}
