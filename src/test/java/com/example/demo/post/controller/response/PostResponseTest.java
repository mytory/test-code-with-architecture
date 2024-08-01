package com.example.demo.post.controller.response;

import com.example.demo.post.domain.Post;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PostResponseTest {
    @Test
    public void Post로_응답을_생성할_수_있다() {
        // given
        User writer = User.builder()
                .id(1L)
                .email("mytory@gmail.com")
                .nickname("mytory")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        Post post = Post.builder()
                .id(1L)
                .content("Hello World")
                .writer(writer)
                .build();

        // when
        PostResponse postResponse = PostResponse.from(post);

        // then
        assertThat(postResponse.getContent()).isEqualTo("Hello World");
        assertThat(postResponse.getWriter().getEmail()).isEqualTo("mytory@gmail.com");
        assertThat(postResponse.getWriter().getNickname()).isEqualTo("mytory");
        assertThat(postResponse.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
}
