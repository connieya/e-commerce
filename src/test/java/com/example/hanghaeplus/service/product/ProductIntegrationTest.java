package com.example.hanghaeplus.service.product;


import com.example.hanghaeplus.repository.product.ProductEntity;
import com.example.hanghaeplus.repository.user.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductIntegrationTest {


    @DisplayName("")
    @Test
    void getRankProduct(){
        // given
        UserEntity user1 = UserEntity.create("건희", 1000000L);
        ProductEntity product1 = ProductEntity.create("감자", 1000L, 1000L);
        ProductEntity product2 = ProductEntity.create("감자", 1000L, 1000L);
        ProductEntity product3 = ProductEntity.create("감자", 1000L, 1000L);
        ProductEntity product4 = ProductEntity.create("감자", 1000L, 1000L);

        // when


        //then
    }
}
