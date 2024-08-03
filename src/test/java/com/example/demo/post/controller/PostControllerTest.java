package com.example.demo.post.controller;

import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import com.example.demo.post.controller.response.PostResponse;
import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.mock.TestContainer;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class PostControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private TestContainer testContainer;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        TestUuidHolder uuidHolder = new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        TestClockHolder clockHolder = new TestClockHolder(1000L);
        testContainer = new TestContainer(uuidHolder, clockHolder);

        User user = User.builder()
                .id(1)
                .email("kok202@naver.com")
                .nickname("kok202")
                .address("Seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(0)
                .build();

        Post post = Post.builder()
                .id(1)
                .content("helloworld")
                .createdAt(1678530673958L)
                .modifiedAt(0)
                .writer(user)
                .build();

        testContainer.userRepository.save(user);
        testContainer.postRepository.save(post);
    }

    @Test
    void 사용자는_게시물을_단건_조회_할_수_있다() {
        // given
        // when
        ResponseEntity<PostResponse> result = this.testContainer.postController.getById(1);
        PostResponse postResponse = result.getBody();
        assert postResponse != null;

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(postResponse.getId()).isEqualTo(1);
        assertThat(postResponse.getContent()).isEqualTo("helloworld");
        assertThat(postResponse.getCreatedAt()).isEqualTo(1678530673958L);
        assertThat(postResponse.getModifiedAt()).isEqualTo(0);
        assertThat(postResponse.getWriter().getId()).isEqualTo(1);
        assertThat(postResponse.getWriter().getEmail()).isEqualTo("kok202@naver.com");
        assertThat(postResponse.getWriter().getNickname()).isEqualTo("kok202");
    }

    @Test
    void 사용자가_존재하지_않는_게시물을_조회할_경우_에러가_난다() throws Exception {
        // given
        // when
        // then
        assertThatThrownBy(() -> this.testContainer.postController.getById(123456789))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 사용자는_게시물을_수정할_수_있다() throws Exception {
        // given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("foobar")
                .build();

        // when
        ResponseEntity<PostResponse> result = this.testContainer.postController.update(1, postUpdate);
        PostResponse postResponse = result.getBody();
        assert postResponse != null;

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(postResponse.getId()).isEqualTo(1);
        assertThat(postResponse.getContent()).isEqualTo("foobar");
        assertThat(postResponse.getWriter().getId()).isEqualTo(1);
        assertThat(postResponse.getWriter().getEmail()).isEqualTo("kok202@naver.com");
        assertThat(postResponse.getWriter().getNickname()).isEqualTo("kok202");
    }
}
