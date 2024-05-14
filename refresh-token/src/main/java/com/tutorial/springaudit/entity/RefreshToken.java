package com.tutorial.springaudit.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity @Table
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private Instant expirationTime;

    @ManyToOne
    private User user;
}
