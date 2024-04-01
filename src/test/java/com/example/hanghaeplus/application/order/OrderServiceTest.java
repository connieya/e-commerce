package com.example.hanghaeplus.application.order;

import com.example.hanghaeplus.infrastructure.coupon.CouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private CouponRepository couponRepository;

    @DisplayName("주문한 상품의 총 가격과 할인 가격을 구한다.")
    @Test
    void createOrder(){
        // given

        // when


        //then
    }

}