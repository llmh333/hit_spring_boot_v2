package com.hit.jobandlogging.controller;

import com.hit.jobandlogging.base.RestApiV1;
import com.hit.jobandlogging.constant.ErrorMessage;
import com.hit.jobandlogging.constant.UrlConstant;
import com.hit.jobandlogging.domain.dto.response.UserResponseDto;
import com.hit.jobandlogging.exception.InternalServerErrorException;
import com.hit.jobandlogging.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestApiV1
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping(UrlConstant.User.GET_USER_BY_ID)
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        UserResponseDto  userResponseDto = userService.getUserById(userId);
        if (userResponseDto != null) {
            return ResponseEntity.ok(userResponseDto);
        } else {
            throw new InternalServerErrorException(ErrorMessage.INTERNAL_SERVER_ERROR);
        }
    }
}
