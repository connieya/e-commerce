package com.example.hanghaeplus.application.order;

import com.example.hanghaeplus.infrastructure.order.OrderLineRepository;
import com.example.hanghaeplus.application.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.mockito.Mockito.*;


@SpringBootTest
public class ProductRankCacheServiceTest {


    @MockBean
    private OrderLineRepository orderLineRepository;

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @Test
    public void testGetRankProductCaching() {
        // 모의 객체의 반환값 설정
        // getRankProduct 메서드 3번 호출
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 3; i++) {
            orderService.getRankProduct();
        }

        verify(orderLineRepository, times(1)).findTop3RankProductsInLast3Days(today.minusDays(3).atStartOfDay(), today.atStartOfDay());
    }
}
