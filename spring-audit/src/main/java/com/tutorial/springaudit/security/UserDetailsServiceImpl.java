package com.tutorial.springaudit.security;

import com.tutorial.springaudit.entity.User;
import com.tutorial.springaudit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByName(username).get();
        if(user != null) {
            UserDetails userDetails = UserPrincipal.build(user);
            return userDetails;
        }
        throw new UsernameNotFoundException("User not found!");
    }
}
