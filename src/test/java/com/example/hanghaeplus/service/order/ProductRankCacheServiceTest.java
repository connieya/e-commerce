package com.example.hanghaeplus.service.order;

import com.example.hanghaeplus.repository.order.OrderLineRepository;
import com.example.hanghaeplus.service.product.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;


@SpringBootTest
public class ProductRankCacheServiceTest {


    @MockBean
    private OrderLineRepository orderLineRepository;

    @Autowired
    private ProductService productService;

    @Test
    public void testGetRankProductCaching() {
        // 모의 객체의 반환값 설정
        // getRankProduct 메서드 3번 호출
        for (int i = 0; i < 3; i++) {
            productService.getRankProduct();
        }

        verify(productService, times(1)).getRankProduct();
    }
}
