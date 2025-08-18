package com.hit.jobandlogging.domain.dto.request;

import com.hit.jobandlogging.constant.ErrorMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class RegisterRequestDto {

    @Pattern(regexp = "^[A-Za-z0-9]{6,}$", message = ErrorMessage.Auth.VALID_FIELD_USERNAME)
    String username;

    @Email(message = ErrorMessage.Auth.VALID_FIELD_EMAIL)
    String email;

    @Pattern(regexp = "^(?=.*[!@#$%^&*(),.?\":{}|<>;])[A-Za-z0-9!@#$%^&*(),.?\":{}|<>;]{6,}$", message = ErrorMessage.Auth.VALID_FIELD_PASSWORD)
    String password;

    @NotNull(message = ErrorMessage.FIELD_CANNOT_BE_NULL)
    String gender;
}
