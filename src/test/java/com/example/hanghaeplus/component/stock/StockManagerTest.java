package com.example.hanghaeplus.component.stock;

import com.example.hanghaeplus.dto.order.OrderPostRequest;
import com.example.hanghaeplus.dto.product.ProductRequestForOrder;
import com.example.hanghaeplus.error.ErrorCode;
import com.example.hanghaeplus.error.exception.order.InsufficientStockException;
import com.example.hanghaeplus.orm.entity.FakeProduct;
import com.example.hanghaeplus.orm.entity.FakeUser;
import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.entity.User;
import com.example.hanghaeplus.orm.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
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

import static org.assertj.core.api.Assertions.*;
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


    // productId 는 auto increment 이기 때문에
    // Product 생성자에  productId 는 파라미터에 없었다.
    // 테스트를 위해 productId 파라미터 추가
    @DisplayName("주문한 수량 만큼 상품의 재고를 차감한다.")
    @Test
    void deductQuantity() {
        Long productId1 = 1L;
        Long productId2 = 2L;
        // given
        Product product1 = FakeProduct.create(productId1, "아이패드", 1000L, 10L);
        Product product2 = FakeProduct.create(productId2, "아이패드2", 2000L, 20L);

        // when
        List<Product> products = List.of(product1, product2);

        Map<Long, Long> stockMap = new HashMap<>();
        stockMap.put(1L, 5L);
        stockMap.put(2L, 5L);


        stockManager.deduct(products, stockMap);

        //then
        assertEquals(product1.getQuantity(), 5L);
        assertEquals(product2.getQuantity(), 15L);
    }


    @DisplayName("주문한 수량 보다 해당 상품의 재고가 작을 때 예외를 발생 시킨다.")
    @Test
    void deductInSufficientQuantity() {
        Long productId1 = 1L;
        Long productId2 = 2L;
        // given
        Product product1 = FakeProduct.create(productId1, "아이패드", 1000L, 4L);
        Product product2 = FakeProduct.create(productId2, "아이패드2", 2000L, 20L);


        // when
        List<Product> products = List.of(product1, product2);

        Map<Long, Long> stockMap = new HashMap<>();
        stockMap.put(1L, 5L);
        stockMap.put(2L, 5L);


        //then
        assertThatThrownBy(() -> stockManager.deduct(products, stockMap))
                .isInstanceOf(InsufficientStockException.class);
    }


    @DisplayName("주문한 수량 만큼 상품의 재고를 차감한다.")
    @Test
    void deductQuantity2() {
        Long productId1 = 1L;
        Long productId2 = 2L;
        // given
        Product product1 = FakeProduct.create(productId1, "감자", 1000L, 10L);
        Product product2 = FakeProduct.create(productId2, "당근", 2000L, 20L);

        ProductRequestForOrder request1 = ProductRequestForOrder
                .of(productId1, 5L);
        ProductRequestForOrder request2 = ProductRequestForOrder.of(productId2, 5L);

        List<ProductRequestForOrder> forOrders = List.of(request1, request2);
        User user = FakeUser.create(1L, "건희", 10000L);
        // when
        OrderPostRequest orderPostRequest = OrderPostRequest.builder()
                .userId(user.getId())
                .products(forOrders)
                .build();
        stockManager.deduct(orderPostRequest);

        //then
        assertEquals(product1.getQuantity(), 5L);
        assertEquals(product2.getQuantity(), 15L);
    }
}