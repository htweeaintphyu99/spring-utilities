package com.tutorial.springaudit.security;

import com.tutorial.springaudit.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserPrincipal implements UserDetails {

    private Long id;
    private String name;
    private String role;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal build(User user) {
        Set<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
        return new UserPrincipal(user.getId(), user.getName(), user.getPassword(), authorities);
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
