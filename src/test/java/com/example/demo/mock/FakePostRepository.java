package com.example.demo.mock;

import com.example.demo.post.domain.Post;
import com.example.demo.post.service.port.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakePostRepository implements PostRepository {

    private final AtomicLong autoIncrementId = new AtomicLong(1);
    private final List<Post> data = new ArrayList<>();

    public Optional<Post> findById(long id) {
        return data.stream().filter(post -> post.getId() == id).findFirst();
    }

    public Post save(Post post) {
        data.stream().filter(item -> item.getId() == post.getId()).findFirst().ifPresent(data::remove);

        Post newPost = Post.builder()
                .id((post.getId() == 0) ? autoIncrementId.getAndIncrement() : post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .writer(post.getWriter())
                .build();

        data.add(newPost);

        return newPost;
    }
}
