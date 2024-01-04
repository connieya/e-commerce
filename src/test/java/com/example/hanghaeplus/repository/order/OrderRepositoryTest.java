package com.example.hanghaeplus.repository.order;

import com.example.hanghaeplus.repository.product.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("")
    @Test
    void test(){
        // given


        // when


        //then
    }
}
