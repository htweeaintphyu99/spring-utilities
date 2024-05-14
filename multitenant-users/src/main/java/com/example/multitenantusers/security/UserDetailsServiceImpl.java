package com.example.multitenantusers.security;

import com.example.multitenantusers.model.User;
import com.example.multitenantusers.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		try {
			Optional<User> user = userRepository.findByUsername(username);
			if (user.isEmpty()) {
				throw new UsernameNotFoundException("User not found with username " + username);
			}
			return UserPrincipal.build(user.get());
		} catch (Exception e) {
			System.err.println("Error occurs");
			return null;
		}
	}
}