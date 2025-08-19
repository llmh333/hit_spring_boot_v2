package com.hit.jobandlogging.exception;

import com.hit.jobandlogging.base.VsResponseUtil;
import com.hit.jobandlogging.constant.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String,String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach((fieldError) -> {
            String message = messageSource.getMessage(fieldError.getDefaultMessage(),null, LocaleContextHolder.getLocale());
            errors.put(fieldError.getField(),  message );
        });

        return VsResponseUtil.error(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<?> handleHandlerMethodValidationException(HandlerMethodValidationException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getAllErrors().forEach(error -> {
            String field = null;
            String message = error.getDefaultMessage();

            if (error instanceof FieldError fieldError) {
                field = fieldError.getField();
            } else if (error instanceof ObjectError objectError) {
                field = objectError.getObjectName();
            }
            message = messageSource.getMessage(message,null, LocaleContextHolder.getLocale());

            errors.put(field != null ? field : "parameter", message);
        });

        return VsResponseUtil.error(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<?> handleInternalServerErrorException(InternalServerErrorException e) {
        String message = messageSource.getMessage(e.getMessage(), null, LocaleContextHolder.getLocale());
        return VsResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException e) {
        String message = messageSource.getMessage(e.getMessage(), null, LocaleContextHolder.getLocale());
        return VsResponseUtil.error(HttpStatus.UNAUTHORIZED, message);
    }

   @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        String message = messageSource.getMessage(ex.getMessage(), ex.getParams(), LocaleContextHolder.getLocale());
        return VsResponseUtil.error(HttpStatus.NOT_FOUND, message);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handleConflictException(ConflictException ex) {
        String message = messageSource.getMessage(ex.getMessage(), ex.getParams(), LocaleContextHolder.getLocale());
        return VsResponseUtil.error(HttpStatus.CONFLICT, message);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex) {
        String message = messageSource.getMessage(ex.getMessage(), ex.getParams(), LocaleContextHolder.getLocale());
        return VsResponseUtil.error(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        String message = messageSource.getMessage(ErrorMessage.INTERNAL_SERVER_ERROR, null, LocaleContextHolder.getLocale());
        return VsResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
