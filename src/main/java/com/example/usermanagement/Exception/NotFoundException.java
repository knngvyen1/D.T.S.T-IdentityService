package com.example.usermanagement.Exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseRestException {
    public NotFoundException(String errorMessage) {
        super(HttpStatus.NOT_FOUND, errorMessage);
    }
}
