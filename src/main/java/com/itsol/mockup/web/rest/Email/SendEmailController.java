package com.itsol.mockup.web.rest.Email;

import com.itsol.mockup.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping(value = "/email")
public class SendEmailController {

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/testSendEmail" , method = RequestMethod.GET)
    public void sendEmail(){

        try {
            emailService.sendEmail("wintermmo97@gmail.com", "Test Subject", "TestMessage");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
