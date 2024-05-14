package com.example.multitenantusers.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "database_config")
public class DatabaseConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String url;

    private String username;

    private String password;

    @Column(name = "driver_class_name")
    private String driverClassName;

    @Column(name = "tenant_id")
    private String tenantId;

    public DatabaseConfig() {
    }

    public DatabaseConfig(String url, String username, String password, String driverClassName, String tenantId) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
        this.tenantId = tenantId;
    }
}
