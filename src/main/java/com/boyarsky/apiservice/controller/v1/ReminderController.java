package com.boyarsky.apiservice.controller.v1;

import com.boyarsky.apiservice.dto.ApiErrorDto;
import com.boyarsky.apiservice.dto.reminder.CreateReminderDto;
import com.boyarsky.apiservice.security.UserPrincipal;
import com.boyarsky.apiservice.service.GoogleReminderService;
import com.boyarsky.apiservice.service.impl.exception.AuthorizedClientNotFoundException;
import com.boyarsky.apiservice.service.impl.exception.CalendarNotExistException;
import com.boyarsky.apiservice.service.impl.exception.UserNotFoundException;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.calendar.model.Event;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static com.boyarsky.apiservice.controller.ApiConstants.API_VERSION_1;
import static java.lang.String.format;

@RestController
@RequestMapping(API_VERSION_1 + "/reminders")
public class ReminderController {
    public static final String EVENT_ID = "eventId";
    public static final String DAILY_RECURRENCE_ID = "daily";
    public static final String WEEKLY_RECURRENCE_ID = "weekly";

    private final GoogleReminderService reminderService;

    public ReminderController(GoogleReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @GetMapping
    @Operation(description = "Get all reminders for user in range between startDate and endDate", security = @SecurityRequirement(name = "JWT"))
    public ResponseEntity<List<Event>> getAllReminders(@AuthenticationPrincipal UserPrincipal user,
                                                       @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startDateTime,
                                                       @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endDateTime) {
        return ResponseEntity.ok().body(reminderService.getAllWithinTimeSlot(user.getId(), startDateTime, endDateTime));
    }

    @PostMapping
    @Operation(description = "Create new reminder for user", security = @SecurityRequirement(name = "JWT"))
    public ResponseEntity<Event> create(@AuthenticationPrincipal UserPrincipal user, @Valid @RequestBody CreateReminderDto newReminder) {
        if (newReminder.getRecurrence() != null) {
            validateRecurrence(newReminder.getRecurrence());
            String rfc5545Recurrence = convertRecurrenceToRfc5545(newReminder.getRecurrence());
            newReminder.setRecurrence(Collections.singletonList(rfc5545Recurrence));
        }
        Event createdEvent = reminderService.create(user.getId(), newReminder);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    private String convertRecurrenceToRfc5545(List<String> recurrence) {
        //converting only first recurrence, in future should be considered day of the week for weekly recurrence
        String recur = recurrence.get(0);
        if (recur.equals(DAILY_RECURRENCE_ID)) {
            return "RRULE:FREQ=DAILY";
        } else if (recur.equals(WEEKLY_RECURRENCE_ID)) {
            return "RRULE:FREQ=WEEKLY;BYDAY=MO";
        } else {
            return null;
        }
    }

    private void validateRecurrence(List<String> recurrence) {
        if (!recurrence.stream().allMatch(this::isSupportedRecurrenceOption)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, format("Illegal recurrence format found in: %s", recurrence));
        }
    }

    private boolean isSupportedRecurrenceOption(String recc) {
        if (recc.toLowerCase().equals(DAILY_RECURRENCE_ID)) {
            return true;
        }
        return recc.toLowerCase().equals(WEEKLY_RECURRENCE_ID);
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
