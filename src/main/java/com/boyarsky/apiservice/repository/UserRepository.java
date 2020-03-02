package com.boyarsky.apiservice.repository;

import com.boyarsky.apiservice.entity.User;

public interface UserRepository {

    User getUserByEmail(String email);

    boolean removeUserByEmail(String email);

    User getUserById(long id);

    User save(User user);
}
