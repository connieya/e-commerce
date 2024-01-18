package com.example.hanghaeplus.repository.order;

import com.example.hanghaeplus.controller.product.response.OrderProductRankResponse;
import com.example.hanghaeplus.repository.product.response.OrderProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLineEntity,Long> {

    OrderLineEntity findByProductId(Long productId);

    @Query("select new com.example.hanghaeplus.repository.product.response.OrderProductResponse(o.productId,o.quantity,o.price)" +
            " from OrderLineEntity o where o.order.id = :orderId")
    List<OrderProductResponse> findByOrderId(@Param("orderId") Long orderId);

    @Query("select o.productId from OrderLineEntity o " +
            "group by o.productId " +
            "order by count(o.productId) desc limit 3")
    List<Long> findTop3ProductIdsByCount();

    @Query("select new com.example.hanghaeplus.controller.product.response.OrderProductRankResponse(o.productId,p.name,count(o.productId)) " +
            "from OrderLineEntity o inner join ProductEntity p " +
            "on o.productId = p.id " +
            "group by o.productId  " +
            "order by count(o.productId) desc limit 3"
    )
    List<OrderProductRankResponse> findTop3RankProductsByCount();

    @Query("select new com.example.hanghaeplus.controller.product.response.OrderProductRankResponse(o.productId,p.name,count(o.productId)) " +
            "from OrderLineEntity o inner join ProductEntity p " +
            "on o.productId = p.id " +
            "where o.createdDate >= :startDate and o.createdDate < :endDate " +
            "group by o.productId  " +
            "order by count(o.productId) desc limit 3"
    )
    List<OrderProductRankResponse> findTop3RankProductsInLast3Days(@Param("startDate") LocalDateTime startDate , @Param("endDate") LocalDateTime endDate);

}
