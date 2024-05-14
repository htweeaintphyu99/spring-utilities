package com.tutorial.springaudit.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.JwtParser;


import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getName())
                .setId(String.valueOf(userPrincipal.getId()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

    }

    public Claims resolveClaims(HttpServletRequest request) {

        String jwt = getJwt(request);
        if(jwt != null) {
            return Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwt)
                    .getBody();
        }
        return null;
    }

    public String getJwt(HttpServletRequest request) {

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.replace("Bearer ", "");
        }
        return null;
    }
}
