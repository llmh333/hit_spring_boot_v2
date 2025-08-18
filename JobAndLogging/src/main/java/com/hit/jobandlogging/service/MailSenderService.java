package com.hit.jobandlogging.service;

public interface MailSenderService {
    public boolean sendMail(String to, Object subject, Object body);
}
