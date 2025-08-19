package com.hit.jobandlogging.service;

import com.hit.jobandlogging.domain.dto.request.UpdateUserRequestDto;
import com.hit.jobandlogging.domain.dto.response.UserResponseDto;

public interface UserService {

    public UserResponseDto getUserById(String userId);

    public UserResponseDto updateUser(String userId, UpdateUserRequestDto requestDto);
}
