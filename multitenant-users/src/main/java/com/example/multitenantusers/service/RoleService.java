package com.example.multitenantusers.service;

import com.example.multitenantusers.model.Role;
import com.example.multitenantusers.repository.RoleRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role save(Role role) {
        Role savedRole = roleRepository.save(role);
        return savedRole;
    }

    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}
