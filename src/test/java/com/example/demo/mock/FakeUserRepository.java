package com.example.demo.mock;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.service.port.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class FakeUserRepository implements UserRepository {

    private final AtomicLong autoIncrementId = new AtomicLong(1);
    private final List<User> data = new ArrayList<>();

    public Optional<User> findByEmailAndStatus(String email, UserStatus userStatus) {
        return data.stream()
                .filter(user -> user.getEmail().equals(email) && user.getStatus().equals(userStatus))
                .findFirst();
    }

    public Optional<User> findByIdAndStatus(long id, UserStatus userStatus) {
        log.debug("data = {}", data);
        return data.stream()
                .filter(user -> user.getId() == id && user.getStatus().equals(userStatus))
                .findFirst();
    }

    public Optional<User> findById(long id) {
        return data.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    @Override
    public Optional<User> getById(long writerId) {
        return findByIdAndStatus(writerId, UserStatus.ACTIVE);
    }

    public User save(User user) {
        data.stream().filter(item -> item.getId() == user.getId()).findFirst().ifPresent(data::remove);

        User newUser = User.builder()
                .id((user.getId() == 0) ? autoIncrementId.getAndIncrement() : user.getId())
                .address(user.getAddress())
                .certificationCode(user.getCertificationCode())
                .status(user.getStatus())
                .nickname(user.getNickname())
                .lastLoginAt(user.getLastLoginAt())
                .email(user.getEmail())
                .build();

        data.add(newUser);

        return newUser;
    }
}
