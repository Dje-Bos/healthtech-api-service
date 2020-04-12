package com.boyarsky.apiservice.controller.v1;

import com.boyarsky.apiservice.dto.ApiErrorDto;
import com.boyarsky.apiservice.dto.notification.CreateNotificationDto;
import com.boyarsky.apiservice.security.UserPrincipal;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
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
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.ZonedDateTime;
import java.util.List;

import static com.boyarsky.apiservice.controller.ApiConstants.API_VERSION_1;
import static com.boyarsky.apiservice.mapper.NotificationMapper.NOTIFICATION_MAPPER;
import static com.google.api.client.googleapis.javanet.GoogleNetHttpTransport.newTrustedTransport;
import static java.time.temporal.ChronoField.OFFSET_SECONDS;

@RestController
@RequestMapping(API_VERSION_1 + "/notifications")
public class NotificationController {
    private static final Logger LOG = LoggerFactory.getLogger(NotificationController.class);
    public static final String EVENT_ID = "eventId";
    private OAuth2AuthorizedClientService auth2AuthorizedClientService;

    public NotificationController(OAuth2AuthorizedClientService auth2AuthorizedClientService) {
        this.auth2AuthorizedClientService = auth2AuthorizedClientService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllNotifications(@AuthenticationPrincipal UserPrincipal user,
                                                           @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startDateTime,
                                                           @RequestParam("endDate")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endDateTime) throws GeneralSecurityException, IOException {
        OAuth2AuthorizedClient oAuth2AuthorizedClient = getAuthorizedClient(user);
        if (oAuth2AuthorizedClient == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Calendar service = getCalendarApiClient(oAuth2AuthorizedClient);
        List<Event> foundEvents = service.events()
                .list(user.getEmail())
                .setTimeMin(new DateTime(startDateTime.toInstant().toEpochMilli(), startDateTime.getOffset().get(OFFSET_SECONDS) / 60))
                .setTimeMax(new DateTime(endDateTime.toInstant().toEpochMilli(), endDateTime.getOffset().get(OFFSET_SECONDS) / 60))
                .execute()
                .getItems();
        return ResponseEntity.ok()
                .body(foundEvents);
    }

    private OAuth2AuthorizedClient getAuthorizedClient(@AuthenticationPrincipal UserPrincipal user) {
        return auth2AuthorizedClientService.loadAuthorizedClient("google", user.getUsername());
    }

    @PostMapping
    public ResponseEntity<Event> create(@AuthenticationPrincipal UserPrincipal user, @Valid @RequestBody CreateNotificationDto newNotification) throws GeneralSecurityException, IOException {
        OAuth2AuthorizedClient oAuth2AuthorizedClient = getAuthorizedClient(user);
        if (oAuth2AuthorizedClient == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Calendar service = getCalendarApiClient(oAuth2AuthorizedClient);
        Event createdEvent = service.events().insert(user.getEmail(), NOTIFICATION_MAPPER.toEvent(newNotification)).execute();
        return ResponseEntity.ok()
                .body(createdEvent);
    }

    private Calendar getCalendarApiClient(OAuth2AuthorizedClient oAuth2AuthorizedClient) throws GeneralSecurityException, IOException {
        GoogleCredential googleCredential = new GoogleCredential();
        googleCredential.setAccessToken(oAuth2AuthorizedClient.getAccessToken().getTokenValue());
        return new Calendar.Builder(newTrustedTransport(), JacksonFactory.getDefaultInstance(), googleCredential).setApplicationName("HealthTech").build();
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal UserPrincipal user, @PathVariable(EVENT_ID) String eventId) throws GeneralSecurityException, IOException {
        OAuth2AuthorizedClient oAuth2AuthorizedClient = getAuthorizedClient(user);
        if (oAuth2AuthorizedClient == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Calendar service = getCalendarApiClient(oAuth2AuthorizedClient);
        service.events().delete(user.getEmail(), eventId).execute();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> get(@AuthenticationPrincipal UserPrincipal user, @PathVariable(EVENT_ID) String eventId) throws GeneralSecurityException, IOException {
        OAuth2AuthorizedClient oAuth2AuthorizedClient = getAuthorizedClient(user);
        if (oAuth2AuthorizedClient == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Calendar service = getCalendarApiClient(oAuth2AuthorizedClient);
        return ResponseEntity.ok()
                .body(service.events().get(user.getEmail(), eventId).execute());
    }

    @ExceptionHandler(GoogleJsonResponseException.class)
    public ResponseEntity<ApiErrorDto> handleGoogleException(GoogleJsonResponseException e) {
        ApiErrorDto apiError = new ApiErrorDto();
        apiError.setErr(e.getMessage());
        return ResponseEntity.badRequest().body(apiError);
    }
}
