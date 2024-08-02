package com.example.demo.small.user.service;

import com.example.demo.small.mock.FakeMailSender;
import com.example.demo.user.service.CertificationService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CertificationServiceTest {

    @Test
    public void 이메일_제목과_내용이_잘_들어가는지_확인() {

        // given
        FakeMailSender mailSender = new FakeMailSender();
        CertificationService certificationService = new CertificationService(mailSender);

        // when
        certificationService.send("test@test.com", 1L, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

        // then
        assertThat(mailSender.email).isEqualTo("test@test.com");
        assertThat(mailSender.subject).isEqualTo("Please certify your email address");
        assertThat(mailSender.text).contains("http://localhost:8080/api/users/1/verify?certificationCode=aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

    }
}