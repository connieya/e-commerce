package com.example.hanghaeplus.service;

import com.example.hanghaeplus.orm.entity.Point;
import com.example.hanghaeplus.orm.entity.User;
import com.example.hanghaeplus.orm.repository.PointRepository;
import com.example.hanghaeplus.orm.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @Autowired
    PointService pointService;


    @BeforeEach
    public void beforeEach(){

    }

    @DisplayName("주문서를 토대로 결제를 한다")
    @Test
    void processPayment(){
        // given

        // when


        //then
    }


}