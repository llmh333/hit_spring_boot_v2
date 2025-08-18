package com.hit.jobandlogging.repository;

import com.hit.jobandlogging.domain.entity.OtpForgotPassword;
import com.hit.jobandlogging.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpForgotPasswordRepository extends JpaRepository<OtpForgotPassword, Long> {
    OtpForgotPassword findByUser(User user);

    OtpForgotPassword findByOtp(String otp);

    OtpForgotPassword findByTokenConfirmPassword(String tokenConfirmPassword);
}
