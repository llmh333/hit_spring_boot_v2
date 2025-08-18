package com.hit.jobandlogging.service;

import com.hit.jobandlogging.domain.dto.request.LoginRequestDto;
import com.hit.jobandlogging.domain.dto.request.LogoutRequestDto;
import com.hit.jobandlogging.domain.dto.request.RegisterRequestDto;
import com.hit.jobandlogging.domain.dto.response.LoginResponseDto;
import com.hit.jobandlogging.domain.dto.response.UserResponseDto;

public interface AuthService {

    public UserResponseDto register(RegisterRequestDto registerRequestDto);

    public LoginResponseDto login(LoginRequestDto loginRequestDto);

    public boolean logout(LogoutRequestDto logoutRequestDto);
}
