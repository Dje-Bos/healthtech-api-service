package com.boyarsky.apiservice.service;

import com.boyarsky.apiservice.entity.Role;

public interface UserRolesService {
    Role createOrGetByUid(String uid);
}
