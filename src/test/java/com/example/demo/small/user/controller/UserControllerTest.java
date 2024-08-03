package com.example.demo.small.user.controller;

import com.example.demo.small.mock.TestClockHolder;
import com.example.demo.small.mock.TestContainer;
import com.example.demo.small.mock.TestUuidHolder;
import com.example.demo.user.controller.response.MyProfileResponse;
import com.example.demo.user.controller.response.UserResponse;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserUpdate;
import com.example.demo.user.exception.CertificationCodeNotMatchedException;
import com.example.demo.user.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class UserControllerTest {

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

        User user2 = User.builder()
                .id(2)
                .email("kok303@naver.com")
                .nickname("kok303")
                .address("Seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .status(UserStatus.PENDING)
                .lastLoginAt(0L)
                .build();

        testContainer.userRepository.save(user1);
        testContainer.userRepository.save(user2);
    }

    @Test
    void 사용자는_특정_유저의_정보를_개인정보는_소거된채_전달_받을_수_있다() throws Exception {
        // given
        // when
        ResponseEntity<UserResponse> result = testContainer.userController.getById(1);
        UserResponse userResponse = result.getBody();

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(userResponse).isNotNull();
        assert userResponse != null;
        assertThat(userResponse.getId()).isEqualTo(1);
        assertThat(userResponse.getEmail()).isEqualTo("kok202@naver.com");
        assertThat(userResponse.getNickname()).isEqualTo("kok202");
        assertThat(userResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(userResponse.getLastLoginAt()).isEqualTo(0L);
    }

    @Test
    void 사용자는_존재하지_않는_유저의_아이디로_api_호출할_경우_404_응답을_받는다() throws Exception {
        // given
        // when
        // then
        assertThatThrownBy(() -> testContainer.userController.getById(12345))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() throws Exception {
        // given
        // when
        this.testContainer.userController.verifyEmail(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");

        // then
        User user = this.testContainer.userRepository.getById(2).orElseThrow();
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_인증_코드가_일치하지_않을_경우_권한_없음_에러를_내려준다() throws Exception {
        // given
        // when
        // then
        assertThatThrownBy(() -> this.testContainer.userController.verifyEmail(2, "invalid-code"))
                .isInstanceOf(CertificationCodeNotMatchedException.class);
    }

    @Test
    void 사용자는_내_정보를_불러올_때_개인정보인_주소도_갖고_올_수_있다() throws Exception {
        // given
        // when
        ResponseEntity<MyProfileResponse> result = this.testContainer.userController.getMyInfo("kok202@naver.com");
        MyProfileResponse myProfileResponse = result.getBody();

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assert myProfileResponse != null;
        assertThat(myProfileResponse.getId()).isEqualTo(1);
        assertThat(myProfileResponse.getEmail()).isEqualTo("kok202@naver.com");
        assertThat(myProfileResponse.getNickname()).isEqualTo("kok202");
        assertThat(myProfileResponse.getAddress()).isEqualTo("Seoul");
        assertThat(myProfileResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_내_정보를_수정할_수_있다() throws Exception {
        // given
        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("kok202-n")
                .address("Pangyo")
                .build();

        // when
        ResponseEntity<MyProfileResponse> result = this.testContainer.userController.updateMyInfo("kok202@naver.com", userUpdate);
        MyProfileResponse myProfileResponse = result.getBody();
        assert myProfileResponse != null;
        assertThat(myProfileResponse.getNickname()).isEqualTo("kok202-n");
        assertThat(myProfileResponse.getAddress()).isEqualTo("Pangyo");

        // then
        User user = this.testContainer.userService.getByEmail("kok202@naver.com");
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getEmail()).isEqualTo("kok202@naver.com");
        assertThat(user.getNickname()).isEqualTo("kok202-n");
        assertThat(user.getAddress()).isEqualTo("Pangyo");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

}
