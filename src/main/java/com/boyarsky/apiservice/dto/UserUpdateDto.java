package com.boyarsky.apiservice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class UserUpdateDto {
    @NotNull
    private LocalDateTime birthDate;
    @NotNull
    private Float height;
}
