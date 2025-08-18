package com.hit.jobandlogging.service.impl;

import com.hit.jobandlogging.constant.ErrorMessage;
import com.hit.jobandlogging.domain.dto.request.ConfirmNewPasswordRequestDto;
import com.hit.jobandlogging.domain.dto.response.VerifiedOtpResponseDto;
import com.hit.jobandlogging.domain.entity.OtpForgotPassword;
import com.hit.jobandlogging.domain.entity.User;
import com.hit.jobandlogging.exception.BadRequestException;
import com.hit.jobandlogging.exception.ConflictException;
import com.hit.jobandlogging.exception.NotFoundException;
import com.hit.jobandlogging.exception.UnauthorizedException;
import com.hit.jobandlogging.repository.OtpForgotPasswordRepository;
import com.hit.jobandlogging.repository.UserRepository;
import com.hit.jobandlogging.security.JwtProvider;
import com.hit.jobandlogging.security.UserPrincipal;
import com.hit.jobandlogging.service.OtpForgotPasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@Log4j2
@RequiredArgsConstructor
public class OtpForgotPasswordServiceImpl implements OtpForgotPasswordService {

    private final MailSenderServiceImpl mailSenderService;
    private final OtpForgotPasswordRepository otpForgotPasswordRepository;
    private final UserRepository userRepository;
    private final SecureRandom secureRandom = new SecureRandom();
    private final TemplateEngine templateEngine;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean sendOtp(String receivedEmail) {
        log.info("Start sending OTP for email {}", receivedEmail);
        User user = userRepository.findByEmail(receivedEmail);
        if (user == null) {
            throw new NotFoundException(ErrorMessage.User.EMAIL_NOT_FOUND);
        }

        String otp = generateOtp(user);
        Context context = new Context();
        context.setVariable("userName", user.getUsername());
        context.setVariable("otpCode", otp);
        String templateOtp = templateEngine.process("otp_send_email.html", context);

        if (mailSenderService.sendMail(user.getEmail(), "Hello, This is an automated message", templateOtp)) {
            OtpForgotPassword otpForgotPassword = new OtpForgotPassword();
            otpForgotPassword.setOtp(otp);
            otpForgotPassword.setUser(user);
            otpForgotPassword.setExpiry(LocalDateTime.now().plusMinutes(5));
            otpForgotPassword.setVerified(Boolean.FALSE);
            otpForgotPasswordRepository.save(otpForgotPassword);
            log.info("OTP code {} has been sent to email {}", otp, receivedEmail);
            return true;
        }

        log.info("Send OTP code {} to email {} failed", otp, receivedEmail);
        return false;
    }

    @Override
    public VerifiedOtpResponseDto verifyOtp(String otp) {
        log.info("Start verifying OTP {}", otp);
        OtpForgotPassword otpForgotPassword = otpForgotPasswordRepository.findByOtp(otp);
        if (otpForgotPassword == null) {
            log.info("Authentication failed, incorrect OTP code");
            throw new UnauthorizedException(ErrorMessage.ForgotPassword.OTP_INCORRECT);
        }

        User user = otpForgotPassword.getUser();
        UserPrincipal userPrincipal = UserPrincipal.create(user);

        otpForgotPassword.setVerified(Boolean.TRUE);
        otpForgotPassword.setExpiryToken(LocalDateTime.now().plusMinutes(5));
        otpForgotPassword.setTokenConfirmPassword(jwtProvider.generateToken(userPrincipal, false));
        otpForgotPasswordRepository.save(otpForgotPassword);
        log.info("OTP verified {}", otp);

        return VerifiedOtpResponseDto.builder()
                .expiryToken(otpForgotPassword.getExpiryToken())
                .tokenConfirmPassword(otpForgotPassword.getTokenConfirmPassword())
                .build();
    }

    @Override
    public boolean confirmNewPassword(ConfirmNewPasswordRequestDto requestDto, String tokenResetPassword) {

        OtpForgotPassword otpForgotPassword = otpForgotPasswordRepository.findByTokenConfirmPassword(tokenResetPassword);
        if (otpForgotPassword == null) {
            log.info("Authentication failed, incorrect OTP code");
            throw new UnauthorizedException(ErrorMessage.ForgotPassword.OTP_INCORRECT);
        }

        if (!requestDto.getNewPassword().equals(requestDto.getConfirmNewPassword())) {
            log.info("The both password are not match");
            throw new BadRequestException(ErrorMessage.ForgotPassword.PASSWORD_NOT_MATCH);
        }

        User user = otpForgotPassword.getUser();

        log.info("Changing new password for account {}", user.getUsername());
        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));

        if (userRepository.save(user) == null) {
            log.info("Changing new password for account {} failed", user.getUsername());
            return false;
        }

        otpForgotPasswordRepository.delete(otpForgotPassword);

        log.info("Changing new password for account {} successful", user.getUsername());
        return true;
    }

    private String generateOtp(User user) {
        log.info("Start generating OTP for email {}", user.getEmail());
        OtpForgotPassword otpForgotPassword = otpForgotPasswordRepository.findByUser(user);
        if (otpForgotPassword != null){
            if (otpForgotPassword.getVerified() == Boolean.FALSE && LocalDateTime.now().isAfter(otpForgotPassword.getExpiry())) {
                otpForgotPasswordRepository.delete(otpForgotPassword);
            } else if (otpForgotPassword.getVerified() == Boolean.TRUE && LocalDateTime.now().isAfter(otpForgotPassword.getExpiryToken())) {
                otpForgotPasswordRepository.delete(otpForgotPassword);
            } else {
                log.info("You can only get new otp after {}", otpForgotPassword.getExpiry().toString());
                throw new ConflictException(ErrorMessage.ForgotPassword.DUPLICATE_GET_OTP, new String[]{String.valueOf(otpForgotPassword.getExpiry())});
            }
        }
        secureRandom.setSeed(System.currentTimeMillis());
        int otp = secureRandom.nextInt(9999);
        log.info("Generated OTP {} for email {}", otp, user.getEmail());
        return String.format("%04d", otp);
    }
}
