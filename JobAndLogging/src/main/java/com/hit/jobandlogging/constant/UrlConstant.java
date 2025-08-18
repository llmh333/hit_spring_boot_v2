package com.hit.jobandlogging.constant;

public class UrlConstant {

    public static final String BASE_URL = "/api/v1";

    public static class Auth {
        public static final String PRE_FIX = "/auth";
        public static final String LOGIN = PRE_FIX + "/login";
        public static final String LOGOUT = PRE_FIX + "/logout";
        public static final String REGISTER = PRE_FIX + "/register";
    }

    public static class ForgotPassword {
        public static final String PRE_FIX = "/forgot-password";
        public static final String GET_OTP = PRE_FIX + "/get-otp";
        public static final String VERIFY_OTP = PRE_FIX + "/verify-otp";
        public static final String CONFIRM_NEW_PASSWORD = PRE_FIX + "/confirm-new-password";
    }

    public static class User {
        public static final String PRE_FIX = "/users";
        public static final String GET_USER_BY_ID = PRE_FIX + "/{userId}";
    }
}
