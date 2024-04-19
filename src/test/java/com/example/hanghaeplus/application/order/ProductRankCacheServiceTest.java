package com.example.hanghaeplus.application.order;

import com.example.hanghaeplus.infrastructure.order.OrderLineJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.mockito.Mockito.*;


@SpringBootTest
public class ProductRankCacheServiceTest {


    @MockBean
    private OrderLineJpaRepository orderLineRepository;


    @Autowired
    private PopularProductService popularProductService;

    @Test
    public void testGetRankProductCaching() {
        // 모의 객체의 반환값 설정
        // getRankProduct 메서드 3번 호출
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 3; i++) {
            popularProductService.getPopularProduct();
        }

        verify(orderLineRepository, times(1)).findPopularProduct(today.minusDays(3).atStartOfDay(), today.atStartOfDay());
    }
}
