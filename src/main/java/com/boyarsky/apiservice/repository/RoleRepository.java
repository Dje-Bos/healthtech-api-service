package com.boyarsky.apiservice.repository;

import com.boyarsky.apiservice.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> getRoleByUid(String uid);
}
