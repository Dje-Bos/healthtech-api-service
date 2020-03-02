package com.boyarsky.apiservice.repository.impl;

import com.boyarsky.apiservice.entity.Role;
import com.boyarsky.apiservice.entity.User;
import com.boyarsky.apiservice.repository.DataJpaUserRepository;
import com.boyarsky.apiservice.repository.UserRepository;
import com.boyarsky.apiservice.repository.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class UserRepositoryImpl implements UserRepository {

    private DataJpaUserRepository repository;
    private UserRolesRepository userRolesRepository;

    @Autowired
    public UserRepositoryImpl(DataJpaUserRepository repository, UserRolesRepository userRolesRepository) {
        this.repository = repository;
        this.userRolesRepository = userRolesRepository;
    }

    @Override
    public User getUserByEmail(String email) {
        return repository.getUserByEmail(email);
    }

    @Override
    public boolean removeUserByEmail(String email) {
        return repository.removeUserByEmail(email) != 0;
    }

    @Override
    public User getUserById(long id) {
        return repository.getUserById(id);
    }


    @Override
    @Transactional
    public User save(User userModel) {
        if (userModel.getRoles() != null) {
            Set<Role> roles = userModel.getRoles().stream().map(Role::getUid).map(userRolesRepository::getRoleEntityByUid).collect(Collectors.toSet());
            userModel.setRoles(roles);
        }
        return repository.save(userModel);
    }
}
