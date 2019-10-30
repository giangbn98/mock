package com.itsol.mockup.services;

import javax.mail.MessagingException;

public interface EmailService {
    void sendEmail(String toEmail, String subject, String message) throws MessagingException;

}
