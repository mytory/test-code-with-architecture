package com.example.demo.mock;

import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.common.service.port.UuidHolder;
import com.example.demo.post.controller.PostController;
import com.example.demo.post.controller.PostCreateController;
import com.example.demo.post.service.PostService;
import com.example.demo.post.service.port.PostRepository;
import com.example.demo.user.controller.UserController;
import com.example.demo.user.controller.UserCreateController;
import com.example.demo.user.service.CertificationService;
import com.example.demo.user.service.UserService;
import com.example.demo.user.service.port.MailSender;
import com.example.demo.user.service.port.UserRepository;

public class TestContainer {
    public final UserRepository userRepository;
    public final PostRepository postRepository;
    public final UserService userService;
    public final PostService postService;
    public final UserController userController;
    public final UserCreateController userCreateController;
    public final PostController postController;
    public final PostCreateController postCreateController;
    public final MailSender mailSender;

    public TestContainer(UuidHolder uuidHolder, ClockHolder clockHolder) {
        this.userRepository = new FakeUserRepository();
        this.postRepository = new FakePostRepository();
        this.mailSender = new FakeMailSender();
        userService = UserService.builder()
                .userRepository(this.userRepository)
                .certificationService(new CertificationService(mailSender))
                .clockHolder(clockHolder)
                .uuidHolder(uuidHolder)
                .build();
        postService = PostService.builder()
                .userRepository(userRepository)
                .postRepository(postRepository)
                .build();

        userController = new UserController(userService);
        userCreateController = new UserCreateController(userService);
        postController = new PostController(postService);
        postCreateController = new PostCreateController(postService);

    }
}
