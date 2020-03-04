package com.boyarsky.apiservice.repository;

import com.boyarsky.apiservice.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User getUserByEmail(String email);

    int removeUserByEmail(String email);

    User getUserById(long id);
}
