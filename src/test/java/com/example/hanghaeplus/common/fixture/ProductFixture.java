package com.example.hanghaeplus.common.fixture;

import com.example.hanghaeplus.application.product.command.ProductCreate;
import com.example.hanghaeplus.domain.product.Product;

public class ProductFixture {

    /*
    *  PRODUCT CREATE DTO
    *
    * */
    public static ProductCreate ONION_CREATE =  ProductCreate
            .builder()
            .name("양파")
            .price(1000L)
            .quantity(5L)
            .build();

    public static ProductCreate POTATO_CREATE =  ProductCreate
            .builder()
            .name("감자")
            .price(2000L)
            .quantity(15L)
            .build();

    public static ProductCreate CARROT_CREATE = ProductCreate
            .builder()
            .name("당근")
            .price(3000L)
            .quantity(20L)
            .build();

    public static ProductCreate MUSHROOM_CREATE = ProductCreate
            .builder()
            .name("당근")
            .price(5000L)
            .quantity(30L)
            .build();


    public static ProductCreate ONION_MANY_CREATE =  ProductCreate
            .builder()
            .name("양파")
            .price(1000L)
            .quantity(300L)
            .build();

    public static ProductCreate POTATO_MANY_CREATE =  ProductCreate
            .builder()
            .name("감자")
            .price(2000L)
            .quantity(300L)
            .build();

    public static ProductCreate CARROT_MANY_CREATE = ProductCreate
            .builder()
            .name("당근")
            .price(3000L)
            .quantity(300L)
            .build();


    /*
    *  ENTITY
    * */
    public static Product CLEAN_CODE_BOOK = Product.of(1L,"클린 코드 도서",25000L, 100L);
    public static Product OOP_BOOK = Product.of(2L,"OOP 도서",22000L, 100L);
    public static Product TDD_BOOK = Product.of(3L,"TDD 도서",35000L, 100L);
    public static Product CLEAN_ARCHITECTURE_BOOK = Product.of(4L,"클린 아키텍처 도서",42000L, 100L);
    public static Product DESIGN_PATTERN_BOOK = Product.of(5L,"디자인 패턴 도서",35000L, 100L);


    public static Product ONION() {
        return Product.of("양파",1000L,300L);
    }

    public static Product POTATO() {
        return Product.of("감자",2000L,300L);
    }

    public static Product CARROT() {
        return Product.of("당근",3000L,300L);
    }

    public static Product MUSHROOM() {
        return Product.of("버섯",5000L,300L);
    }

    public static Product SWEET_POTATO() {
        return Product.of("고구마",2000L,300L);
    }
}
