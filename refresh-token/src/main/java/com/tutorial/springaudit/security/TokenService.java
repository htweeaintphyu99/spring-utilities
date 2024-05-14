package com.tutorial.springaudit.security;

import com.tutorial.springaudit.entity.RefreshToken;
import com.tutorial.springaudit.entity.User;
import com.tutorial.springaudit.exception.BadRequestException;
import com.tutorial.springaudit.exception.ForbiddenException;
import com.tutorial.springaudit.repository.RefreshTokenRepository;
import com.tutorial.springaudit.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.refresh.expiration}")
    private long refreshExpiration;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public String createToken(UserPrincipal userPrincipal) {
        return Jwts.builder()
                .setSubject(userPrincipal.getName())
                .setId(String.valueOf(userPrincipal.getId()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

    }

    public Claims resolveClaims(HttpServletRequest request) {

        try {
            String jwt = getJwt(request);
            if (jwt != null) {
                return Jwts
                        .parser()
                        .setSigningKey(secretKey)
                        .parseClaimsJws(jwt)
                        .getBody();
            }
            return null;
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
            throw new ForbiddenException("Expired JWT Token");
        }
    }

    public String getJwt(HttpServletRequest request) {

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.replace("Bearer ", "");
        }
        return null;
    }

    public String createRefreshToken(UserPrincipal userPrincipal) {

        String token = UUID.randomUUID().toString();
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new RuntimeException("User not found!"));
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setExpirationTime(Instant.now().plusMillis(refreshExpiration));
        refreshToken.setUser(user);
        refreshTokenRepository.save(refreshToken);

        return token;
    }

    public String generateJwtToken(String refreshToken) {
        RefreshToken  token = refreshTokenRepository.findByToken(refreshToken).orElseThrow(() -> new BadRequestException("Refresh Token not found!"));
        if(token.getExpirationTime().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new ForbiddenException("Token is expired!");
        }
        User user = token.getUser();
        return Jwts.builder()
                .setSubject(user.getName())
                .setId(String.valueOf(user.getId()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
