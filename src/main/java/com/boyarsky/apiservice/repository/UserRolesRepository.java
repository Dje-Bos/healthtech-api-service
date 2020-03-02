package com.boyarsky.apiservice.repository;

import com.boyarsky.apiservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRolesRepository extends JpaRepository<Role, Long> {
    Role getRoleEntityByUid(String uid);
}
