package com.example.demo.user.service;

import com.example.demo.user.infrastructure.FakeMailSender;
import com.example.demo.user.service.port.MailSender;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CertificationServiceTest {

    @Test
    public void 이메일_제목과_내용이_잘_들어가는지_확인() {

        FakeMailSender mailSender = new FakeMailSender();
        CertificationService certificationService = new CertificationService(mailSender);
        certificationService.send("test@test.com", 1L, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

        assertThat(mailSender.email).isEqualTo("test@test.com");
        assertThat(mailSender.subject).isEqualTo("Please certify your email address");
        assertThat(mailSender.text).contains("http://localhost:8080/api/users/1/verify?certificationCode=aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

    }
}
