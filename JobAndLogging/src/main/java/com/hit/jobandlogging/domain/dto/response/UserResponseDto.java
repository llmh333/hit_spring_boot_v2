package com.hit.jobandlogging.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserResponseDto {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
}
