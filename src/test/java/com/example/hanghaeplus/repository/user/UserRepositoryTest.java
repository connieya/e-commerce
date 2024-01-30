package com.example.hanghaeplus.repository.user;

import com.example.hanghaeplus.common.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.service.user.request.UserCreate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.example.hanghaeplus.common.error.ErrorCode.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.*;

//@DataJpaTest
@SpringBootTest
@Transactional
class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }


    @DisplayName("잔액 조회")
    @Test
    void getCurrentPoint() {
        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .point(5000L)
                .build();

        User user = User.create(userCreate);
        User savedUser = userRepository.save(user);
        assertThat(savedUser.getPoint()).isEqualTo(5000L);
    }

    @DisplayName("유저 조회")
    @Test
    void findById() {
        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .point(5000L)
                .build();
        User user = User.create(userCreate);
        User savedUser = userRepository.save(user);
        User findUser = userRepository.findById(savedUser.getId()).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        assertThat(savedUser.getId()).isEqualTo(findUser.getId());
    }

}