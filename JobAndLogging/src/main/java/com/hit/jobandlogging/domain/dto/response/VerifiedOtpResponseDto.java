package com.hit.jobandlogging.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class VerifiedOtpResponseDto {

    private String tokenConfirmPassword;
    private LocalDateTime expiryToken;

}
