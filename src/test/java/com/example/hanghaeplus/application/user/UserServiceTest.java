package com.example.hanghaeplus.application.user;

import com.example.hanghaeplus.application.user.command.UserCreate;
import com.example.hanghaeplus.application.user.command.UserRecharge;
import com.example.hanghaeplus.common.auth.JwtProvider;
import com.example.hanghaeplus.common.error.exception.EntityAlreadyExistException;
import com.example.hanghaeplus.common.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.domain.user.UserTokens;
import com.example.hanghaeplus.infrastructure.user.PasswordEncoder;
import com.example.hanghaeplus.presentation.user.request.LoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.hanghaeplus.common.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @DisplayName("회원 등록 중 다른 회원이 사용 하고 있는 이메일이 있으면 예외가 발생 한다.")
    @Test
    void register_exceptionWithExistingEmail() {
        // given
        given(userRepository.findByEmail("gunny6026@naver.com"))
                .willReturn(Optional.of(CONY));

        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .nickname("geonhee")
                .email("gunny6026@naver.com")
                .build();
        // when , then
        assertThatThrownBy(
                () -> userService.register(userCreate)
        ).isInstanceOf(EntityAlreadyExistException.class);

    }

    @DisplayName("회원 등록 중 다른 회원이 사용 하고 있는 닉네임이 있으면 예외가 발생 한다.")
    @Test
    void register_exceptionWithExistingNickname() {
        // given
        given(userRepository.findByNickname("cony"))
                .willReturn(Optional.of(CONY));

        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .nickname("cony")
                .email("geonhee@naver.com")
                .build();
        // when , then
        assertThatThrownBy(
                () -> userService.register(userCreate)
        ).isInstanceOf(EntityAlreadyExistException.class);

    }

    @DisplayName("포인트 잔액을 충전 할 회원이 존재 하지 않으면 예외가 발생 한다.")
    @Test
    void recharge_exceptionWithNotFoundUser() {
        // given
        given(userRepository.findById(1000L))
                .willReturn(Optional.empty());
        // when , then
        UserRecharge userRecharge = UserRecharge
                .builder()
                .id(1000L)
                .point(500000L)
                .build();
        assertThatThrownBy(
                () ->
                        userService.rechargePoint(userRecharge)
        ).isInstanceOf(EntityNotFoundException.class);

    }

    @Test
    @DisplayName("존재하지 않는 이메일 입력 시 로그인에 실패한다.")
    void loginFail_notFoundUser() {
        // given
        given(userRepository.findByEmail("test@naver.com"))
                .willReturn(Optional.empty());

        LoginRequest loginRequest = new LoginRequest("test@naver.com", "1234");
        // when , then
        assertThatThrownBy(
                ()-> userService.login(loginRequest)
                ).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("일치하지 않는 비밀번호 입력시 로그인에 실패한다.")
    void loginFail_InvalidPassword() {
        // given
        given(userRepository.findByEmail("lee@gmail.com"))
                .willReturn(Optional.of(LEE));
        LoginRequest loginRequest = new LoginRequest("lee@gmail.com", "44444");
        given(passwordEncoder.matches(loginRequest.getPassword(),LEE.getPassword()))
                .willReturn(false);
        // when , then
        assertThatThrownBy(
                ()-> userService.login(loginRequest)
                ).isInstanceOf(UserException.InValidPasswordException.class);
    }

    @Test
    @DisplayName("로그인에 성공한다.")
    void loginSuccess() {
        // given
        given(userRepository.findByEmail("lee@gmail.com"))
                .willReturn(Optional.of(LEE));
        LoginRequest loginRequest = new LoginRequest("lee@gmail.com", "1234");
        given(passwordEncoder.matches(loginRequest.getPassword(),LEE.getPassword()))
                .willReturn(true);
        final UserTokens userTokens = new UserTokens("refreshToken","accessToken");
        given(jwtProvider.generateToken(LEE.getEmail()))
                .willReturn(userTokens);

        // when
        UserTokens result = userService.login(loginRequest);

        // then
        assertThat(result.getAccessToken()).isEqualTo("accessToken");
        assertThat(result.getRefreshToken()).isEqualTo("refreshToken");
    }

}