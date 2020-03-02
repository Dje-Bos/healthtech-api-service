package com.boyarsky.apiservice.repository;

import com.boyarsky.apiservice.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface DataJpaUserRepository extends CrudRepository<User, Long> {

    User getUserByEmail(String email);

    @Transactional
    @Modifying
    int removeUserByEmail(String email);

    User getUserById(long id);
}
