package com.example.hanghaeplus.component.stock;

import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockManagerTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private StockManager stockManager;
    @DisplayName("주문한 수량 만큼 상품의 재고를 차감한다.")
    @Test
    void deductQuantity(){
        Long productId1 = 1L;
        Long productId2 = 2L;
        // given
        Product product1 = Product.create(productId1,"아이패드",1000L,10L);
        Product product2 = Product.create(productId2,"아이패드2",2000L,20L);

//        given(productRepository.findAllById(List.of(productId1,productId2))).willReturn(List.of(product1,product2));

        // when
        List<Product> products = List.of(product1, product2);

        Map<Long, Long> stockMap = new HashMap<>();
        stockMap.put(1L,5L);
        stockMap.put(2L,5L);


        stockManager.deduct(products,stockMap);

        //then
        assertEquals( product1.getQuantity(),5L);
        assertEquals( product2.getQuantity(),15L);
    }

}