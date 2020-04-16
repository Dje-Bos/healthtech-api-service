package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.dto.notification.CreateReminderDto;
import com.boyarsky.apiservice.entity.user.User;
import com.boyarsky.apiservice.repository.UserRepository;
import com.boyarsky.apiservice.service.GoogleReminderService;
import com.boyarsky.apiservice.service.impl.exception.AuthorizedClientNotFoundException;
import com.boyarsky.apiservice.service.impl.exception.CalendarNotExistException;
import com.boyarsky.apiservice.service.impl.exception.UserNotFoundException;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventReminder;
import lombok.SneakyThrows;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static com.boyarsky.apiservice.mapper.ReminderMapper.NOTIFICATION_MAPPER;
import static com.google.api.client.googleapis.javanet.GoogleNetHttpTransport.newTrustedTransport;
import static java.lang.String.format;
import static java.time.temporal.ChronoField.OFFSET_SECONDS;

@Service
public class GoogleReminderServiceImpl implements GoogleReminderService {
    private OAuth2AuthorizedClientService auth2AuthorizedClientService;
    private UserRepository userRepository;

    public GoogleReminderServiceImpl(OAuth2AuthorizedClientService auth2AuthorizedClientService, UserRepository userRepository) {
        this.auth2AuthorizedClientService = auth2AuthorizedClientService;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    @SneakyThrows(IOException.class)
    public Event create(Long userId, CreateReminderDto newReminder) {
        User user = findUser(userId);
        OAuth2AuthorizedClient oAuth2AuthorizedClient = getOAuthAuthorizedClient(user);
        Calendar calendarApiClient = getCalendarApiClient(oAuth2AuthorizedClient);

        Event newEvent = NOTIFICATION_MAPPER.toEvent(newReminder);
        addBeforeEventReminder(newEvent);
        createRemindersCalendarIfNotExists(user, calendarApiClient);
        return calendarApiClient.events().insert(user.getCalendarId(), newEvent).execute();
    }

    private void addBeforeEventReminder(Event event) {
        EventReminder beforeEventReminder = new EventReminder();
        beforeEventReminder.setMinutes(0);
        beforeEventReminder.setMethod("popup");
        Event.Reminders reminders = new Event.Reminders();
        reminders.setUseDefault(false);
        reminders.setOverrides(Collections.singletonList(beforeEventReminder));
        event.setReminders(reminders);
    }

    private User findUser(Long userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException(format("User for id = '%d' is not found", userId));
        }
        return user;
    }

    private OAuth2AuthorizedClient getOAuthAuthorizedClient(User user) {
        OAuth2AuthorizedClient oAuth2AuthorizedClient = auth2AuthorizedClientService.loadAuthorizedClient("google", user.getEmail());
        if (oAuth2AuthorizedClient == null) {
            throw new AuthorizedClientNotFoundException("No authorized oauth2 client found for user with id = " + user.getId());
        }
        return oAuth2AuthorizedClient;
    }

    @SneakyThrows({GeneralSecurityException.class, IOException.class})
    private Calendar getCalendarApiClient(OAuth2AuthorizedClient oAuth2AuthorizedClient) {
        GoogleCredential googleCredential = new GoogleCredential();
        googleCredential.setAccessToken(oAuth2AuthorizedClient.getAccessToken().getTokenValue());
        return new Calendar.Builder(newTrustedTransport(), JacksonFactory.getDefaultInstance(), googleCredential).setApplicationName("HealthTech").build();
    }

    @SneakyThrows(IOException.class)
    private void createRemindersCalendarIfNotExists(User user, Calendar calendarApiService) {
        if (user.getCalendarId() == null) {
            com.google.api.services.calendar.model.Calendar healthTechCalendar = new com.google.api.services.calendar.model.Calendar();
            healthTechCalendar.setSummary("HealthTech Reminders");
            healthTechCalendar.setDescription("Here HealthTech will add your reminders for timely measure you pulse, blood pressure, glucose and etc.");
            com.google.api.services.calendar.model.Calendar createdCalendar = calendarApiService.calendars().insert(healthTechCalendar).execute();
            user.setCalendarId(createdCalendar.getId());
            userRepository.save(user);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @SneakyThrows(IOException.class)
    public Event get(Long userId, String eventId) {
        User user = findUser(userId);
        if (user.getCalendarId() == null) {
            throw new CalendarNotExistException(format("Cannot get event for id = '%s' when user '%s' doesn't have calendar associated", eventId, userId));
        }
        OAuth2AuthorizedClient oAuth2AuthorizedClient = getOAuthAuthorizedClient(user);
        Calendar calendarApiClient = getCalendarApiClient(oAuth2AuthorizedClient);
        return calendarApiClient.events().get(user.getCalendarId(), eventId).execute();
    }

    @Override
    @Transactional(readOnly = true)
    @SneakyThrows(IOException.class)
    public void delete(Long userId, String eventId) {
        User user = findUser(userId);
        if (user.getCalendarId() == null) {
            throw new CalendarNotExistException(format("Cannot delete event for id = '%s' when user '%s' doesn't have calendar associated", eventId, userId));
        }
        OAuth2AuthorizedClient oAuth2AuthorizedClient = getOAuthAuthorizedClient(user);
        Calendar calendarApiClient = getCalendarApiClient(oAuth2AuthorizedClient);
        calendarApiClient.events().delete(user.getCalendarId(), eventId).execute();
    }

    @Override
    @Transactional(readOnly = true)
    @SneakyThrows(IOException.class)
    public List<Event> getAllWithinTimeSlot(Long userId, ZonedDateTime startDateTime, ZonedDateTime endDateTime) {
        User user = findUser(userId);
        if (user.getCalendarId() == null) {
            return Collections.emptyList();
        }
        OAuth2AuthorizedClient oAuth2AuthorizedClient = getOAuthAuthorizedClient(user);
        Calendar calendarApiService = getCalendarApiClient(oAuth2AuthorizedClient);
        return calendarApiService.events()
                .list(user.getCalendarId())
                .setTimeMin(toGoogleDateTime(startDateTime))
                .setTimeMax(toGoogleDateTime(endDateTime))
                .execute()
                .getItems();
    }

    private DateTime toGoogleDateTime(ZonedDateTime startDateTime) {
        return new DateTime(startDateTime.toInstant().toEpochMilli(), startDateTime.getOffset().get(OFFSET_SECONDS) / 60);
    }
}
