package com.example.multitenantusers.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.SignatureException;


import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecretKey}")
    private String jwtSecretKey;

    @Value("${app.jwtExpiration}")
    private int jwtExpiration;

    public String createJwtToken(Authentication authentication) {

        Date tokenValidity = new Date((new Date()).getTime() + jwtExpiration * 1000);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        final String authorities = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder().setId(Long.toString(userPrincipal.getId()))
                .claim("username", userPrincipal.getName())
                .claim("roles", authorities).setIssuedAt(new Date()).setExpiration(tokenValidity)
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey).compact();

    }

    public Claims resolveClaims(HttpServletRequest request) {

        String jwt = getJwt(request);

        JwtParser jwtParser = Jwts.parser().setSigningKey(jwtSecretKey);
        try {
            if (jwt != null) {

                Claims claims = jwtParser.parseClaimsJws(jwt).getBody();
                return claims;

            }
        } catch (SignatureException e) {
            log.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty -> Message: {}", e);
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

    public String getTenant(HttpServletRequest req) {

        Claims claims = resolveClaims(req);

        if (claims != null) {
            return claims.get("username", String.class);
        }
        return null;
    }
}