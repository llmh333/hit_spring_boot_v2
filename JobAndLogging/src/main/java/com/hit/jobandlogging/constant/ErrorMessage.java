package com.hit.jobandlogging.constant;

public class ErrorMessage {

    public static final String FIELD_CANNOT_BE_NULL = "valid.field.cannot.be.null";
    public static final String FIELD_CANNOT_BE_BLANK= "valid.field.cannot.be.blank";
    public static final String INTERNAL_SERVER_ERROR = "exception.internal.server.error";

    public static class Auth {
        public static final String VALID_FIELD_USERNAME = "valid.username";
        public static final String VALID_FIELD_EMAIL = "valid.email";
        public static final String VALID_FIELD_PASSWORD = "valid.password";
        public static final String INVALID_TOKEN = "invalid.token";
        public static final String EXPIRED_TOKEN = "invalid.expired.token";
        public static final String USERNAME_OR_PASSWORD_INCORRECT = "exception.username.or.password.incorrect";
        public static final String UNAUTHORIZED = "exception.unauthorized";
    }

    public static class User {
        public static final String USERNAME_NOT_FOUND = "exception.username.not.found";
        public static final String EMAIL_NOT_FOUND = "exception.user.email.not.found";
        public static final String ID_NOT_FOUND = "exception.user.id.not.found";
        public static final String USERNAME_ALREADY_EXISTS = "exception.user.username.already.exists";
        public static final String EMAIL_ALREADY_EXISTS = "exception.user.email.already.exists";
    }

    public static class Role {
        public static final String ROLE_NOT_FOUND = "exception.role.not.found";
    }

    public static class ForgotPassword {
        public static final String PASSWORD_NOT_MATCH = "exception.both.password.not.match";
        public static final String EXPIRED_TOKEN_RESET_PASSWORD="exception.confirm.new.password";
        public static final String OTP_INCORRECT = "exception.otp.incorrect";
        public static final String DUPLICATE_GET_OTP = "exception.duplicate.get.otp";
    }
}
