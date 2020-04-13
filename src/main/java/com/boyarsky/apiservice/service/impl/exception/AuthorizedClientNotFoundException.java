package com.boyarsky.apiservice.service.impl.exception;

public class AuthorizedClientNotFoundException extends RuntimeException {
    public AuthorizedClientNotFoundException(String message) {
        super(message);
    }
}
