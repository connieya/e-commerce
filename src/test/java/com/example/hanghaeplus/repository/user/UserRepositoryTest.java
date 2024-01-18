package com.example.hanghaeplus.repository.user;

import com.example.hanghaeplus.common.error.exception.EntityNotFoundException;
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
        User user = User.create("건희", 5000L);
        User savedUser = userRepository.save(user);
        assertThat(savedUser.getPoint()).isEqualTo(5000L);
    }

    @DisplayName("유저 조회")
    @Test
    void findById() {
        User user = User.create("건희", 5000L);
        User savedUser = userRepository.save(user);
        User findUser = userRepository.findById(savedUser.getId()).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        assertThat(savedUser.getId()).isEqualTo(findUser.getId());
    }

}