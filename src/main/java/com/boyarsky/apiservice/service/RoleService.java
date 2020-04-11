package com.boyarsky.apiservice.service;

import com.boyarsky.apiservice.entity.user.Role;

public interface RoleService {
    Role createOrGetByUid(String uid);
}
