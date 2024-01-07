package com.example.hanghaeplus.service.order;

import com.example.hanghaeplus.controller.product.response.OrderProductRankResponse;
import com.example.hanghaeplus.service.product.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Sql("/sql/product-rank-service-data.sql")
public class ProductRankServiceTest {

    @Autowired
    private ProductService productService;

    @DisplayName("최근 3일간 상위 상품 3개를 조회 한다.")
    @Test
    void findTop3ProductsInLast3Days(){
        // given   // when
        List<OrderProductRankResponse> rankProduct = productService.getRankProduct();
        //최근 3일 동안 product_id 3 당근 - 3번 , product_id 1 양파 - 2번  , product_id  2 감자 - 2번

        // 제일 많이 주문한 상위 상품
        OrderProductRankResponse orderProductRankResponse = rankProduct.get(0);

        //then
        assertThat(orderProductRankResponse.getName()).isEqualTo("당근");
        assertThat(orderProductRankResponse.getProductId()).isEqualTo(3);
        assertThat(orderProductRankResponse.getOrderCount()).isEqualTo(3);
        assertThat(rankProduct).hasSize(3)
                .extracting("productId","name","orderCount")
                .containsExactlyInAnyOrder(
                        tuple(3L,"당근",3L),
                        tuple(1L,"양파",2L),
                        tuple(2L,"감자",2L)

                );

    }
}
