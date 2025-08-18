package com.hit.jobandlogging.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class VsResponseUtil {

    public static ResponseEntity<?> success(Object data) {
        return success(HttpStatus.OK, data);
    }

    public static ResponseEntity<?> success(HttpStatus httpStatus, Object data) {
        RestData<?> restData = new RestData<>(data);
        return new ResponseEntity<>(restData, httpStatus);
    }

    public static ResponseEntity<?> successWithMessage(Object message) {
        RestData<?> restData = RestData.successWithMessage(message);
        return new ResponseEntity<>(restData, HttpStatus.OK);
    }

    public static ResponseEntity<?> error(HttpStatus httpStatus, Object messages) {
        RestData<?> restData = RestData.error(messages);
        return new ResponseEntity<>(restData, httpStatus);
    }

}
