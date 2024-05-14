package com.example.multitenantusers.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.multitenantusers.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public static UserPrincipal build(User user) {
		UserPrincipal userPrincipal = new UserPrincipal();
		userPrincipal.setId(user.getId());
		userPrincipal.setName(user.getUsername());
		userPrincipal.setPassword(user.getPassword());

		Set<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toSet());

		userPrincipal.setAuthorities(authorities);
		return userPrincipal;
	}

	public UserPrincipal(Long id, String name, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return this.authorities;
	}

	public Long getId() {
		return this.id;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.name;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}