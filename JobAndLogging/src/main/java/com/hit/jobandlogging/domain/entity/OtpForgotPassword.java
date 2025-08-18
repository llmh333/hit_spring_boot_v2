package com.hit.jobandlogging.domain.entity;

import com.hit.jobandlogging.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "opt_forgot_password")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OtpForgotPassword extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String otp;

    @Column(nullable = false)
    LocalDateTime expiry;

    @Column(nullable = false)
    Boolean verified;

    String tokenConfirmPassword;
    LocalDateTime expiryToken;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    User user;
}
