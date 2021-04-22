package com.example.usermanagement.Exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseRestException {
    public BadRequestException(String errorMessage) {
        super(HttpStatus.BAD_REQUEST, errorMessage);
    }
}
