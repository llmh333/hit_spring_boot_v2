package com.hit.jobandlogging.domain.dto.request;

import com.hit.jobandlogging.constant.ErrorMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class UpdateUserRequestDto {

    private String firstName;

    private String lastName;

    @Email(message = ErrorMessage.Auth.VALID_FIELD_EMAIL)
    private String email;

    private String gender;
}
