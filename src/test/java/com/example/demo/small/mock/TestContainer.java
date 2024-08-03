package com.example.demo.small.mock;

import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.common.service.port.UuidHolder;
import com.example.demo.user.controller.UserController;
import com.example.demo.user.service.CertificationService;
import com.example.demo.user.service.UserService;
import com.example.demo.user.service.port.MailSender;
import com.example.demo.user.service.port.UserRepository;

public class TestContainer {
    public final UserRepository userRepository;
    public final UserService userService;
    public final UserController userController;
    public final MailSender mailSender;

    public TestContainer(UuidHolder uuidHolder, ClockHolder clockHolder) {
        this.userRepository = new FakeUserRepository();
        this.mailSender = new FakeMailSender();
        userService = UserService.builder()
                .userRepository(this.userRepository)
                .certificationService(new CertificationService(mailSender))
                .clockHolder(clockHolder)
                .uuidHolder(uuidHolder)
                .build();
        userController = new UserController(userService);
    }
}
