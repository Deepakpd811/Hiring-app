package com.bridgelab.hiringapp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SendMailUtil {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);                  // Set the recipient's email address.
        message.setSubject(subject);        // Set the email subject.
        message.setText(text);              // Set the email body.
        System.out.println(to + " " + subject + " " + text);
        mailSender.send(message);           // Send the email.
    }

}
