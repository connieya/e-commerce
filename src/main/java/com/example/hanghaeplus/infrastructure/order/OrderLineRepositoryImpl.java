package com.example.hanghaeplus.infrastructure.order;

import com.example.hanghaeplus.application.order.OrderLineRepository;
import com.example.hanghaeplus.domain.order.OrderLine;
import com.example.hanghaeplus.infrastructure.product.response.OrderProductResponse;
import com.example.hanghaeplus.domain.order.PopularProduct;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.hanghaeplus.domain.order.QOrderLine.*;
import static com.example.hanghaeplus.domain.product.QProduct.*;

@Repository
@RequiredArgsConstructor
public class OrderLineRepositoryImpl implements OrderLineRepository {

    private final OrderLineJpaRepository orderLineJpaRepository;
    private final JPAQueryFactory queryFactory;


    @Override
    public List<OrderLine> findAll() {
        return orderLineJpaRepository.findAll();
    }

    @Override
    public List<OrderProductResponse> findByOrderId(Long orderId) {
        return queryFactory
                .select(Projections.fields(OrderProductResponse.class ,
                        orderLine.productId
                        , orderLine.quantity.as("count")
                        ,orderLine.price
                        ))
                .from(orderLine)
                .where(orderLine.order.id.eq(orderId))
                .fetch();
    }

    @Override
    public List<PopularProduct> findPopularProduct() {
        return queryFactory
                .select(Projections.fields(PopularProduct.class
                ,orderLine.productId
                        ,product.name
                        , orderLine.productId.count().as("orderCount")
                        ))
                .from(orderLine)
                .innerJoin(product)
                .on(orderLine.productId.eq(product.id))
                .groupBy(orderLine.productId)
                .orderBy(orderLine.productId.count().desc())
                .limit(3)
                .fetch();
    }

    @Override
    public List<PopularProduct> findPopularProduct(LocalDateTime startDate, LocalDateTime endDate) {
        return queryFactory
                .select(Projections.fields(PopularProduct.class
                        ,orderLine.productId
                        ,product.name
                        , orderLine.productId.count().as("orderCount")
                ))
                .from(orderLine)
                .innerJoin(product)
                .on(orderLine.productId.eq(product.id))
                .where(orderLine.createdDate.goe(startDate).and(orderLine.createdDate.loe(endDate)))
                .groupBy(orderLine.productId)
                .orderBy(orderLine.productId.count().desc())
                .limit(3)
                .fetch();
    }
}
