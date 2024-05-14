package com.example.multitenantusers.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		Claims claims = jwtTokenProvider.resolveClaims(request);

		if (claims != null) {

			String roles = claims.get("roles", String.class);
			String username = claims.get("username", String.class);

			Long userId = Long.parseLong(claims.getId());
			List<String> authorityArray = Arrays.asList(roles.split(","));
			List<GrantedAuthority> authorities = authorityArray.stream().map(role -> new SimpleGrantedAuthority(role))
					.collect(Collectors.toList());

			UserPrincipal userPrincipal = new UserPrincipal(userId, username, null, authorities);
			Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);

		}
		filterChain.doFilter(request, response);
	}

}