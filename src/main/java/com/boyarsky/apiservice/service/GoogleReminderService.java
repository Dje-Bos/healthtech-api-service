package com.boyarsky.apiservice.service;

import com.boyarsky.apiservice.dto.reminder.CreateReminderDto;
import com.google.api.services.calendar.model.Event;

import java.time.ZonedDateTime;
import java.util.List;

public interface GoogleReminderService {

    Event create(Long userId, CreateReminderDto newReminder);

    Event get(Long userId, String eventId);

    void delete(Long userId, String eventId);

    List<Event> getAllWithinTimeSlot(Long userId, ZonedDateTime startDateTime, ZonedDateTime endDateTime);
}
