package com.hit.jobandlogging.controller;

import com.hit.jobandlogging.base.RestApiV1;
import com.hit.jobandlogging.base.VsResponseUtil;
import com.hit.jobandlogging.constant.ErrorMessage;
import com.hit.jobandlogging.constant.UrlConstant;
import com.hit.jobandlogging.domain.dto.request.UpdateUserRequestDto;
import com.hit.jobandlogging.domain.dto.response.UserResponseDto;
import com.hit.jobandlogging.exception.InternalServerErrorException;
import com.hit.jobandlogging.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiV1
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping(UrlConstant.User.GET_USER_BY_ID)
    public ResponseEntity<?> getUserById(@PathVariable @NotNull String userId) {
        UserResponseDto  userResponseDto = userService.getUserById(userId);
        if (userResponseDto != null) {
            return ResponseEntity.ok(userResponseDto);
        } else {
            throw new InternalServerErrorException(ErrorMessage.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(UrlConstant.User.UPDATE_USER_BY_ID)
    public ResponseEntity<?> updateUserById(
            @PathVariable @NotNull String userId,
            @RequestBody @Valid UpdateUserRequestDto updateUserRequestDto) {
        UserResponseDto responseDto = userService.updateUser(userId, updateUserRequestDto);
        if (responseDto == null) {
//            throw new InternalServerErrorException(ErrorMessage.INTERNAL_SERVER_ERROR);
        }
        return VsResponseUtil.success(responseDto);
    }
}
