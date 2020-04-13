package com.boyarsky.apiservice.service.impl.exception;

public class CalendarNotExistException extends RuntimeException {
    public CalendarNotExistException(String message) {
        super(message);
    }
}
