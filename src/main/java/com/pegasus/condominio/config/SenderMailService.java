package com.pegasus.condominio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SenderMailService {

    @Autowired
    private JavaMailSender mailSender;

    public  void sendMail(String receiver, String subject, String text) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(receiver);
        email.setSubject(subject);
        email.setText(text);
        mailSender.send(email);
    }
}