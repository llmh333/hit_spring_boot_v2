package com.hit.jobandlogging.domain.dto.request;

import com.hit.jobandlogging.constant.ErrorMessage;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginRequestDto {

    @Pattern(regexp = "^[A-Za-z0-9]{6,}$", message = ErrorMessage.Auth.VALID_FIELD_USERNAME)
    String username;

    @Pattern(regexp = "^(?=.*[!@#$%^&*(),.?\":{}|<>;])[A-Za-z0-9!@#$%^&*(),.?\":{}|<>;]{6,}$", message = ErrorMessage.Auth.VALID_FIELD_PASSWORD)
    String password;
}
