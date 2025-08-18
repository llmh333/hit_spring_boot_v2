package com.hit.jobandlogging.domain.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponseDto {
    private final String tokenType = "Bearer";
    private String accessToken;
    private String refreshToken;
}
