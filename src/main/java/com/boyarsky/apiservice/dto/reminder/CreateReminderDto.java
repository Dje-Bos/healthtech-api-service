package com.boyarsky.apiservice.dto.reminder;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class CreateReminderDto {
    @NotNull
    private String title;
    private String colorId;
    private String description;
    private List<String> recurrence;
    private ZonedDateTime startTime;
}
