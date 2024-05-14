package com.example.multitenantusers.config;

import com.example.multitenantusers.security.JwtAuthenticationEntryPoint;
import com.example.multitenantusers.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig{

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final JwtAuthenticationEntryPoint entryPoint;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
														UserDetailsService userDetailsService) throws Exception{
		
		AuthenticationManagerBuilder builder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
		builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		AuthenticationManager authenticationManager = builder.build();
		
		return authenticationManager;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
		http.exceptionHandling((exception)-> exception.authenticationEntryPoint(entryPoint));
		http.sessionManagement(securityContext -> securityContext.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.authorizeHttpRequests(authorize ->
				authorize.requestMatchers("/login").permitAll().anyRequest().authenticated());
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		http.headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

		http.httpBasic(Customizer.withDefaults());

		return http.build();
	}
}