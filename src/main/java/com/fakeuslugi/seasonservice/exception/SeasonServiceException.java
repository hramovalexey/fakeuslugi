package com.fakeuslugi.seasonservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

// Custom Exception for more precise error handling
public class SeasonServiceException extends RuntimeException {

    @Getter
    private final HttpStatus httpStatus;

    public SeasonServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
