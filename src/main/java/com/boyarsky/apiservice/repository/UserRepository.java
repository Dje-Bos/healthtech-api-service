package com.boyarsky.apiservice.repository;

import com.boyarsky.apiservice.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getUserByEmail(String email);

    int removeUserByEmail(String email);

    User getUserById(long id);
}
