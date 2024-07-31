package com.example.demo.user.service;

import com.example.demo.user.infrastructure.UserEntity;
import com.example.demo.user.service.port.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CertificationService {
    private final MailSender mailSender;

    public void send(String email, Long id, String certificationCode) {
        String subject = "Please certify your email address";
        String text = "Please click the following link to certify your email address: " + getCertificationUrl(id, certificationCode);
        mailSender.send(email, subject, text);
    }

    private String getCertificationUrl(long id, String certificationCode) {
        return "http://localhost:8080/api/users/" + id + "/verify?certificationCode=" + certificationCode;
    }
}
