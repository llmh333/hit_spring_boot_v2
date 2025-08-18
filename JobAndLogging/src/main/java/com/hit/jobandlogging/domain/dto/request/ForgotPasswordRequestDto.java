package com.hit.jobandlogging.domain.dto.request;

import com.hit.jobandlogging.constant.ErrorMessage;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public class ForgotPasswordRequestDto {

    @NotNull(message = ErrorMessage.FIELD_CANNOT_BE_NULL)
    private String email;
}
