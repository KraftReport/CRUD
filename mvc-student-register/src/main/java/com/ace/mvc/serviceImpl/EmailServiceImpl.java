package com.ace.mvc.serviceImpl;

import com.ace.mvc.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender mailSender;

    @Override
    public String generateCode() {
        var random = new Random();
        var code = random.nextInt(10000);
        return String.valueOf(code);
    }

    @Override
    public void sendEmail(String email, String code) {
        var sendMail = new SimpleMailMessage();
        sendMail.setTo(email);
        sendMail.setSubject("Sending OTP code");
        sendMail.setText("Your OTP code is "+code);
        mailSender.send(sendMail);
    }
}
