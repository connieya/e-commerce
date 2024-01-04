package com.example.hanghaeplus.component.stock;

import com.example.hanghaeplus.controller.order.request.OrderPostRequest;
import com.example.hanghaeplus.controller.order.request.ProductRequestForOrder;
import com.example.hanghaeplus.repository.user.FakeUser;
import com.example.hanghaeplus.repository.product.Product;
import com.example.hanghaeplus.repository.user.User;
import com.example.hanghaeplus.repository.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class StockServiceTest {

    @Autowired
    private StockManager stockManager;

    @Autowired
    private ProductRepository productRepository;


    Product requestProduct1;
    Product requestProduct2;


    @BeforeEach
    void setUp(){
        Product productPotato = Product.create("감자", 1000L, 10L);
        Product productCarrot = Product.create( "당근", 2000L, 20L);

        List<Product> products = List.of(productPotato, productCarrot);
        List<Product> products1 = productRepository.saveAll(products);
        requestProduct1=  products1.get(0);
        requestProduct2 = products1.get(1);
    }

    @DisplayName("주문한 수량에 맞게 재고를 차감한다.")
    @Test
    void deductQuantity(){
        // given
        ProductRequestForOrder request1 = ProductRequestForOrder
                .of(requestProduct1.getId(), 5L);
        ProductRequestForOrder request2 = ProductRequestForOrder.of(requestProduct2.getId(), 5L);

        List<ProductRequestForOrder> forOrders = List.of(request1, request2);
        User user = FakeUser.create(1L, "건희", 10000L);
        // when
        OrderPostRequest orderPostRequest = OrderPostRequest.builder()
                .userId(user.getId())
                .products(forOrders)
                .build();
        stockManager.deduct(orderPostRequest);


        Product findProduct1 = productRepository.findById(requestProduct1.getId()).get();
        Product findProduct2 = productRepository.findById(requestProduct2.getId()).get();
        //then
        assertEquals(findProduct1.getQuantity(), 5L);
        assertEquals(findProduct2.getQuantity(), 15L);
    }


}
