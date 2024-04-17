package com.example.hanghaeplus.common.fixture;

import com.example.hanghaeplus.domain.product.Product;

public class ProductFixture {

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
