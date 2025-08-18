package com.hit.jobandlogging.domain.dto.request;

import com.hit.jobandlogging.constant.ErrorMessage;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ConfirmNewPasswordRequestDto {

    @Pattern(regexp = "^(?=.*[!@#$%^&*(),.?\":{}|<>;])[A-Za-z0-9!@#$%^&*(),.?\":{}|<>;]{6,}$", message = ErrorMessage.Auth.VALID_FIELD_PASSWORD)
    private final String newPassword;

    @Pattern(regexp = "^(?=.*[!@#$%^&*(),.?\":{}|<>;])[A-Za-z0-9!@#$%^&*(),.?\":{}|<>;]{6,}$", message = ErrorMessage.Auth.VALID_FIELD_PASSWORD)
    private final String confirmNewPassword;
}
