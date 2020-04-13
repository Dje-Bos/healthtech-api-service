package com.boyarsky.apiservice.controller.v1;

import com.boyarsky.apiservice.dto.ApiErrorDto;
import com.boyarsky.apiservice.dto.notification.CreateReminderDto;
import com.boyarsky.apiservice.security.UserPrincipal;
import com.boyarsky.apiservice.service.GoogleReminderService;
import com.boyarsky.apiservice.service.impl.exception.AuthorizedClientNotFoundException;
import com.boyarsky.apiservice.service.impl.exception.CalendarNotExistException;
import com.boyarsky.apiservice.service.impl.exception.UserNotFoundException;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.calendar.model.Event;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.List;

import static com.boyarsky.apiservice.controller.ApiConstants.API_VERSION_1;

@RestController
@RequestMapping(API_VERSION_1 + "/reminders")
public class ReminderController {
    public static final String EVENT_ID = "eventId";
    private GoogleReminderService reminderService;

    public ReminderController(GoogleReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllNotifications(@AuthenticationPrincipal UserPrincipal user,
                                                           @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startDateTime,
                                                           @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endDateTime) {
        return ResponseEntity.ok().body(reminderService.getAllWithinTimeSlot(user.getId(), startDateTime, endDateTime));
    }

    @PostMapping
    public ResponseEntity<Event> create(@AuthenticationPrincipal UserPrincipal user, @Valid @RequestBody CreateReminderDto newReminder) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reminderService.create(user.getId(), newReminder));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal UserPrincipal user, @PathVariable(EVENT_ID) String eventId) {
        reminderService.delete(user.getId(), eventId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> get(@AuthenticationPrincipal UserPrincipal user, @PathVariable(EVENT_ID) String eventId) {
        return ResponseEntity.ok(reminderService.get(user.getId(), eventId));
    }

    @ExceptionHandler(GoogleJsonResponseException.class)
    public ResponseEntity<ApiErrorDto> handleGoogleException(GoogleJsonResponseException e) {
        ApiErrorDto apiError = new ApiErrorDto();
        apiError.setErr(e.getMessage());
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(AuthorizedClientNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleOAuth2Exception(AuthorizedClientNotFoundException e) {
        return getApiErrorDtoResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleUserException(UserNotFoundException e) {
        return getApiErrorDtoResponseEntity(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(CalendarNotExistException.class)
    public ResponseEntity<ApiErrorDto> handleCalendarException(CalendarNotExistException e) {
        return getApiErrorDtoResponseEntity(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private ResponseEntity<ApiErrorDto> getApiErrorDtoResponseEntity(String message, HttpStatus status) {
        ApiErrorDto apiError = new ApiErrorDto();
        apiError.setErr(message);
        return ResponseEntity.status(status).body(apiError);
    }
}
