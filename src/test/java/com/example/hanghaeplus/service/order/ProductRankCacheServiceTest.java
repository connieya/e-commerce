package com.example.hanghaeplus.service.order;

import com.example.hanghaeplus.controller.product.response.OrderProductRankResponse;
import com.example.hanghaeplus.repository.order.OrderLineRepository;
import com.example.hanghaeplus.service.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductRankCacheServiceTest {


    @Mock
    private ProductService productService;

    @Test
    public void testGetRankProductCaching() {
        // 모의 객체의 반환값 설정
        // getRankProduct 메서드 3번 호출
        productService.getRankProduct();
        productService.getRankProduct();
        productService.getRankProduct();

        verify(productService, times(1)).getRankProduct();
    }
}
