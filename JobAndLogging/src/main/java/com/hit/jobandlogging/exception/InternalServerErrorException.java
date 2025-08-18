package com.hit.jobandlogging.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@Getter
@Setter
public class InternalServerErrorException extends RuntimeException {

    private String message;
    private String params[];
    private HttpStatus status;


    public InternalServerErrorException(String message) {
        super(message);
        this.message = message;
        this.status = HttpStatus.NOT_FOUND;
    }

    public InternalServerErrorException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public InternalServerErrorException(String message, String[] params) {
        super(message);
        this.message = message;
        this.params = params;
        this.status = HttpStatus.NOT_FOUND;
    }

    public InternalServerErrorException(String message, HttpStatus status, String[] params) {
        super(message);
        this.message = message;
        this.status = status;
        this.params = params;
    }
}
