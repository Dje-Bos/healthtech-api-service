package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.entity.Role;
import com.boyarsky.apiservice.repository.UserRolesRepository;
import com.boyarsky.apiservice.service.UserRolesService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRoleServiceImpl implements UserRolesService {
    private UserRolesRepository userRolesRepository;

    public UserRoleServiceImpl(UserRolesRepository userRolesRepository) {
        this.userRolesRepository = userRolesRepository;
    }

    @Override
    public Role createOrGetByUid(String uid) {
        Optional<Role> userRole = userRolesRepository.getRoleByUid(uid);
        if (userRole.isPresent()) {
            return userRole.get();
        } else {
            Role role = new Role(uid);
            return userRolesRepository.save(role);
        }
    }
}
