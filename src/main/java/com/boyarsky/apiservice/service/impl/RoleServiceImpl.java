package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.entity.user.Role;
import com.boyarsky.apiservice.repository.RoleRepository;
import com.boyarsky.apiservice.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role createOrGetByUid(String uid) {
        Optional<Role> userRole = roleRepository.getRoleByUid(uid);
        if (userRole.isPresent()) {
            return userRole.get();
        } else {
            Role role = new Role(uid);
            return roleRepository.save(role);
        }
    }
}
