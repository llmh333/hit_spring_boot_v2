package com.hit.jobandlogging.domain.dto.request;

import com.hit.jobandlogging.constant.ErrorMessage;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LogoutRequestDto {

    @NotNull(message = ErrorMessage.FIELD_CANNOT_BE_NULL)
    private String accessToken;

    @NotNull(message = ErrorMessage.FIELD_CANNOT_BE_NULL)
    private String refreshToken;
}
