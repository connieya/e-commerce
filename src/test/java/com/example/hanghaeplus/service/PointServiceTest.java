package com.example.hanghaeplus.service;

import com.example.hanghaeplus.orm.entity.Point;
import com.example.hanghaeplus.orm.entity.User;
import com.example.hanghaeplus.orm.repository.PointRepository;
import com.example.hanghaeplus.orm.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PointServiceTest {

    @Autowired
    PointRepository pointRepository;
    @Autowired
    UserRepository userRepository;


    @Test
    void 잔액충전() {
        User user = new User("박건희");
        Point point = new Point(10000L);
        user.setPoint(point);
        userRepository.save(user);
    }

    @Test
    void 잔액조회() {

    }


}