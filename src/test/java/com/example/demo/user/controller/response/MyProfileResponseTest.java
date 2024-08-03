package com.example.demo.user.controller.response;

import com.example.demo.user.controller.response.MyProfileResponse;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MyProfileResponseTest {

    @Test
    public void User로_응답을_생성할_수_있다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("mytory@gmail.com")
                .nickname("mytory")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .lastLoginAt(100L)
                .build();

        // when
        MyProfileResponse myProfileResponse = MyProfileResponse.from(user);

        // then
        assertThat(myProfileResponse.getId()).isEqualTo(1L);
        assertThat(myProfileResponse.getEmail()).isEqualTo("mytory@gmail.com");
        assertThat(myProfileResponse.getNickname()).isEqualTo("mytory");
        assertThat(myProfileResponse.getAddress()).isEqualTo("Seoul");
        assertThat(myProfileResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(myProfileResponse.getLastLoginAt()).isEqualTo(100L);
    }
}
