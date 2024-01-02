package com.example.hanghaeplus.service;


import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductIntegrationTest {


    @DisplayName("")
    @Test
    void getRankProduct(){
        // given
        User user1 = User.create("건희", 1000000L);
        Product product1 = Product.create("감자", 1000L, 1000L);
        Product product2 = Product.create("감자", 1000L, 1000L);
        Product product3 = Product.create("감자", 1000L, 1000L);
        Product product4 = Product.create("감자", 1000L, 1000L);

        // when


        //then
    }
}
