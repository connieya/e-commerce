package com.example.hanghaeplus.orm.repository;

import com.example.hanghaeplus.orm.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
@SpringBootTest
class UserRepositoryTest {



    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PointRepository pointRepository;

//    @AfterEach
//    void tearDown() {
//        pointRepository.deleteAll();
//        userRepository.deleteAll();
//    }


    @DisplayName("사용자의 이름을 통해 사용자를 등록한다.")
    @Test
    void registerUser(){
        // given
        User user = new User("박건희");
        user.addPoint();

        // when
        User savedUser = userRepository.save(user);


        //then
        Assertions.assertThat(user.getName()).isEqualTo(savedUser.getName());
    }

}