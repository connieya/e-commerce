package com.example.hanghaeplus.orm.repository;

import com.example.hanghaeplus.repository.user.User;
import com.example.hanghaeplus.repository.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
        assertThat(savedUser.getCurrentPoint()).isEqualTo(5000L);
    }

}