package com.boyarsky.apiservice.repository.impl;

import com.boyarsky.apiservice.entity.User;
import com.boyarsky.apiservice.repository.DataJpaUserRepository;
import com.boyarsky.apiservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class UserRepositoryImpl implements UserRepository {

    private DataJpaUserRepository repository;

    @Autowired
    public UserRepositoryImpl(DataJpaUserRepository repository) {
        this.repository = repository;
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
    public User save(User userModel) {
        return repository.save(userModel);
    }
}
