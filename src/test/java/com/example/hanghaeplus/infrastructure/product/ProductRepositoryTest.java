package com.example.hanghaeplus.infrastructure.product;

import com.example.hanghaeplus.application.product.ProductRepository;
import com.example.hanghaeplus.application.product.request.ProductCreate;
import com.example.hanghaeplus.domain.product.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE,
        connection = EmbeddedDatabaseConnection.H2
)
class ProductRepositoryTest {

    @Autowired
    private ProductJpaRepository productRepository;

    Product savedProduct;

    @BeforeEach
    void before() {
        ProductCreate productCreate = ProductCreate.builder()
                .name("아이 패드")
                .price(500000L)
                .quantity(130L)
                .build();

        Product product = Product.create(productCreate);
        savedProduct = productRepository.save(product);
    }

    @DisplayName("상품 이름과 가격, 재고 정보를 통해 상품과 재고를 등록 한다.")
    @Test
    void registerProduct() {
        // given
        ProductCreate productCreate = ProductCreate.builder()
                .name("아이폰 15")
                .price(100000L)
                .quantity(30L)
                .build();


        Product product = Product.create(productCreate);

        // when
        Product savedProduct = productRepository.save(product);

        //then
        assertThat(productCreate.getName()).isEqualTo(savedProduct.getName());
        assertThat(productCreate.getPrice()).isEqualTo(savedProduct.getPrice());
        assertThat(productCreate.getQuantity()).isEqualTo(savedProduct.getQuantity());
    }


    @DisplayName("상품 Id 를 통해 상품 정보를 조회한다.")
    @Test
    void findProductById() {
        // given
        Product product = productRepository.findById(savedProduct.getId()).get();
        // when

        //then
        assertThat(product.getPrice()).isEqualTo(500000L);
        assertThat(product.getName()).isEqualTo("아이 패드");
        assertThat(product.getQuantity()).isEqualTo(130L);

    }

    @DisplayName("상품 Id 를 통해 상품 정보를 조회한다.")
    @Test
    void findAllByPessimisticLock() {

        //given
        List<Product> products = productRepository.findAllByPessimisticLock(List.of(savedProduct.getId()));
        assertThat(products.get(0).getPrice()).isEqualTo(500000L);
        assertThat(products.get(0).getName()).isEqualTo("아이 패드");
        assertThat(products.get(0).getQuantity()).isEqualTo(130L);

    }

}