package com.example.demo.user.controller;

import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import com.example.demo.mock.TestContainer;
import com.example.demo.user.controller.response.UserResponse;
import com.example.demo.user.domain.UserCreate;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class UserCreateControllerTest {

    TestContainer testContainer;

    @BeforeEach
    public void setUp() {
        TestUuidHolder uuidHolder = new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        TestClockHolder clockHolder = new TestClockHolder(1000L);

        testContainer = new TestContainer(uuidHolder, clockHolder);
    }

    @Test
    void 사용자는_회원_가입을_할_수있고_회원가입된_사용자는_PENDING_상태이다() throws Exception {
        // given
        UserCreate userCreate = UserCreate.builder()
            .email("kok202@kakao.com")
            .nickname("kok202")
            .address("Pangyo")
            .build();

        // when
        ResponseEntity<UserResponse> result = testContainer.userCreateController.create(userCreate);
        UserResponse userResponse = result.getBody();
        assert userResponse != null;

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(userResponse.getId()).isEqualTo(1);
        assertThat(userResponse.getEmail()).isEqualTo("kok202@kakao.com");
        assertThat(userResponse.getNickname()).isEqualTo("kok202");
        assertThat(userResponse.getStatus()).isEqualTo(UserStatus.PENDING);
    }

}
