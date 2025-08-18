package com.hit.jobandlogging.controller;

import com.hit.jobandlogging.base.RestApiV1;
import com.hit.jobandlogging.base.VsResponseUtil;
import com.hit.jobandlogging.constant.ErrorMessage;
import com.hit.jobandlogging.constant.UrlConstant;
import com.hit.jobandlogging.domain.dto.request.ConfirmNewPasswordRequestDto;
import com.hit.jobandlogging.domain.dto.response.VerifiedOtpResponseDto;
import com.hit.jobandlogging.service.impl.OtpForgotPasswordServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestApiV1
@RequiredArgsConstructor
public class OtpForgotPasswordController {

    private final OtpForgotPasswordServiceImpl  otpForgotPasswordService;

    @PostMapping(UrlConstant.ForgotPassword.GET_OTP)
    public ResponseEntity<?> getOtp(@RequestParam("email") String email){
        boolean responseGetOtp = otpForgotPasswordService.sendOtp(email);
        if (responseGetOtp){
            return VsResponseUtil.success(null);
        }
        return VsResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(UrlConstant.ForgotPassword.VERIFY_OTP)
    public ResponseEntity<?> verifyOtp(@RequestParam("otp") String otp){
        VerifiedOtpResponseDto responseDto = otpForgotPasswordService.verifyOtp(otp);
        if (responseDto == null) {
            return VsResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.INTERNAL_SERVER_ERROR);
        }
        return VsResponseUtil.success(responseDto);
    }

    @PostMapping(UrlConstant.ForgotPassword.CONFIRM_NEW_PASSWORD)
    public ResponseEntity<?> confirmNewPassword(@RequestBody ConfirmNewPasswordRequestDto requestDto, HttpServletRequest request){
        String tokenResetPassword = request.getHeader("Authorization").substring(7);
        boolean statusConfirmNewPassword =  otpForgotPasswordService.confirmNewPassword(requestDto, tokenResetPassword);
        if (!statusConfirmNewPassword){
            return VsResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.INTERNAL_SERVER_ERROR);
        }
        return VsResponseUtil.success(HttpStatus.NO_CONTENT);
    }
}
