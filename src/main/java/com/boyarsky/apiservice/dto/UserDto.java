package com.boyarsky.apiservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String name;
    private Long id;
    private String email;
    private String pictureUrl;
    private String authType;
    private List<RoleDto> roles;
}
