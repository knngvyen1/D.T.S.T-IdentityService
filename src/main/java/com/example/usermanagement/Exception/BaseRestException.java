package com.example.usermanagement.Exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class BaseRestException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String errorMessage;

    public BaseRestException(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
