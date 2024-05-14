package com.example.multitenantusers.service;

import com.example.multitenantusers.model.User;
import com.example.multitenantusers.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findUser(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
