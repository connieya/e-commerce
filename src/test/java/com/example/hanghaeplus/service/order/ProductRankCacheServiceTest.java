package com.example.hanghaeplus.service.order;

import com.example.hanghaeplus.controller.product.response.OrderProductRankResponse;
import com.example.hanghaeplus.service.product.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Sql("/sql/product-rank-service-data.sql")
public class ProductRankCacheServiceTest {


    @SpyBean
    private ProductService productService;

    @DisplayName("캐싱된 함수를 호출할 경우 , 캐싱된 결과가 반환된다.")
    @Test
    void cacheTest(){
        // given   // when
//        given(productService.getProduct()).willReturn();
        for (int i=0; i<3; i++){
             productService.getRankProduct();
        }
        //then
        verify(productService, atMost(1)).getRankProduct();
    }
}
