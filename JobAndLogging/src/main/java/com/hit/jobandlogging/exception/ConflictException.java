package com.hit.jobandlogging.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
@Getter
@Setter
public class ConflictException extends RuntimeException {

    private HttpStatus status;
    private String message;
    private String[] params;

    public ConflictException(String message) {
        super(message);
        this.message = message;
        this.status = HttpStatus.CONFLICT;
    }

    public ConflictException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public ConflictException(String message, String[] params) {
        super(message);
        this.message = message;
        this.params = params;
        this.status = HttpStatus.CONFLICT;
    }

    public ConflictException(String message, HttpStatus status, String[] params) {
        super(message);
        this.message = message;
        this.status = status;
        this.params = params;
    }
}
