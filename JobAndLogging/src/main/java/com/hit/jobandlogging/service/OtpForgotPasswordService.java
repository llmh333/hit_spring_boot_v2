package com.hit.jobandlogging.service;

import com.hit.jobandlogging.domain.dto.request.ConfirmNewPasswordRequestDto;
import com.hit.jobandlogging.domain.dto.response.VerifiedOtpResponseDto;

public interface OtpForgotPasswordService {
    public boolean sendOtp(String receivedEmail);
    public VerifiedOtpResponseDto verifyOtp(String otp);
    public boolean confirmNewPassword(ConfirmNewPasswordRequestDto requestDto, String tokenResetPassword);
}
