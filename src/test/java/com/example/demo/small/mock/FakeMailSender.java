package com.example.demo.small.mock;

import com.example.demo.user.service.port.MailSender;

@SuppressWarnings("FieldCanBeLocal")
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
