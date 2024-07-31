package com.example.demo.user.infrastructure;

import com.example.demo.user.service.port.MailSender;
import org.springframework.stereotype.Component;

@SuppressWarnings("FieldCanBeLocal")
@Component
public class FakeMailSender implements MailSender {

    public String email;
    public String subject;
    public String text;

    public void send(String email, String subject, String text) {
        this.email = email;
        this.subject = subject;
        this.text = text;
    }
}
