package com.boyarsky.apiservice.dto.notification;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class CreateNotificationDto {
    @NotNull
    private String title;
    private String colorId;
    private String description;
    private List<String> recurrence;
    private ZonedDateTime startTime;
}
