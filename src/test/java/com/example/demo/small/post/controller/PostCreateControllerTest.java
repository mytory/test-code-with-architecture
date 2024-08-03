package com.example.demo.small.post.controller;

import com.example.demo.post.controller.response.PostResponse;
import com.example.demo.post.domain.PostCreate;
import com.example.demo.small.mock.TestClockHolder;
import com.example.demo.small.mock.TestContainer;
import com.example.demo.small.mock.TestUuidHolder;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostCreateControllerTest {

    TestContainer testContainer;

    @BeforeEach
    public void setUp() {
        TestUuidHolder uuidHolder = new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        TestClockHolder clockHolder = new TestClockHolder(1000L);
        testContainer = new TestContainer(uuidHolder, clockHolder);

        User user1 = User.builder()
                .id(1)
                .email("kok202@naver.com")
                .nickname("kok202")
                .address("Seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(0L)
                .build();

        testContainer.userRepository.save(user1);
    }


    @Test
    void 사용자는_게시물을_작성할_수_있다() throws Exception {
        // given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("helloworld")
                .build();

        // when
        ResponseEntity<PostResponse> result = this.testContainer.postCreateController.create(postCreate);
        PostResponse postResponse = result.getBody();

        // then
        assert postResponse != null;
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(postResponse.getId()).isEqualTo(1);
        assertThat(postResponse.getContent()).isEqualTo("helloworld");
        assertThat(postResponse.getWriter().getId()).isEqualTo(1);
        assertThat(postResponse.getWriter().getEmail()).isEqualTo("kok202@naver.com");
        assertThat(postResponse.getWriter().getNickname()).isEqualTo("kok202");
    }
}
