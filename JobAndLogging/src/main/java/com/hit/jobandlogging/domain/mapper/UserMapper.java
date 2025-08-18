package com.hit.jobandlogging.domain.mapper;

import com.hit.jobandlogging.domain.dto.request.RegisterRequestDto;
import com.hit.jobandlogging.domain.dto.response.UserResponseDto;
import com.hit.jobandlogging.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toUserResponseDto(User user);
    User toUser(UserResponseDto userResponseDto);
    User toUser(RegisterRequestDto registerRequestDto);
}
