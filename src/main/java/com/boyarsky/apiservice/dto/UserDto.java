package com.boyarsky.apiservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDto {
    private String name;
    private Long id;
    private String email;
    private String pictureUrl;
    private String authType;
    private List<RoleDto> roles;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthDate;
    private Float height;
}
