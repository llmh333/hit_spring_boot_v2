package com.hit.jobandlogging.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
@Setter
public class NotFoundException extends RuntimeException {

    private HttpStatus status;
    private String message;
    private String[] params;

    public NotFoundException(String message) {
        super(message);
        this.message = message;
        this.status = HttpStatus.NOT_FOUND;
    }

    public NotFoundException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public NotFoundException(String message, String[] params) {
        super(message);
        this.message = message;
        this.params = params;
        this.status = HttpStatus.NOT_FOUND;
    }

    public NotFoundException(String message, HttpStatus status, String[] params) {
        super(message);
        this.message = message;
        this.status = status;
        this.params = params;
    }
}
