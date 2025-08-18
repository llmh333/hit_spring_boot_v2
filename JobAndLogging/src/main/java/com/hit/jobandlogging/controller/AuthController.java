package com.hit.jobandlogging.controller;

import com.hit.jobandlogging.base.RestApiV1;
import com.hit.jobandlogging.base.VsResponseUtil;
import com.hit.jobandlogging.constant.ErrorMessage;
import com.hit.jobandlogging.constant.UrlConstant;
import com.hit.jobandlogging.domain.dto.request.LoginRequestDto;
import com.hit.jobandlogging.domain.dto.request.LogoutRequestDto;
import com.hit.jobandlogging.domain.dto.request.RegisterRequestDto;
import com.hit.jobandlogging.domain.dto.response.LoginResponseDto;
import com.hit.jobandlogging.domain.dto.response.UserResponseDto;
import com.hit.jobandlogging.exception.InternalServerErrorException;
import com.hit.jobandlogging.service.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiV1
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping(UrlConstant.Auth.REGISTER)
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDto requestDto) {
        UserResponseDto responseDto = authService.register(requestDto);
        return VsResponseUtil.success(HttpStatus.CREATED, responseDto);
    }

    @PostMapping(UrlConstant.Auth.LOGIN)
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto requestDto) {
        LoginResponseDto responseDto = authService.login(requestDto);
        return VsResponseUtil.success(HttpStatus.OK, responseDto);
    }

    @PostMapping(UrlConstant.Auth.LOGOUT)
    public ResponseEntity<?> logout(@RequestBody @Valid LogoutRequestDto requestDto) {
        if (authService.logout(requestDto)) {
            return  VsResponseUtil.success(HttpStatus.OK, requestDto);
        } else {
            throw new InternalServerErrorException(ErrorMessage.INTERNAL_SERVER_ERROR);
        }
    }
}
