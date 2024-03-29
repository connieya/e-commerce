package com.example.hanghaeplus.infrastructure.order;

import com.example.hanghaeplus.domain.order.OrderLine;
import com.example.hanghaeplus.presentation.product.response.OrderProductRankResponse;
import com.example.hanghaeplus.infrastructure.product.response.OrderProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine,Long> {

    OrderLine findByProductId(Long productId);

    @Query("select new com.example.hanghaeplus.infrastructure.product.response.OrderProductResponse(o.productId,o.quantity,o.price)" +
            " from OrderLine o where o.order.id = :orderId")
    List<OrderProductResponse> findByOrderId(@Param("orderId") Long orderId);

    @Query("select o.productId from OrderLine o " +
            "group by o.productId " +
            "order by count(o.productId) desc limit 3")
    List<Long> findTop3ProductIdsByCount();

    @Query("select new com.example.hanghaeplus.presentation.product.response.OrderProductRankResponse(o.productId,p.name,count(o.productId)) " +
            "from OrderLine o inner join Product p " +
            "on o.productId = p.id " +
            "group by o.productId  " +
            "order by count(o.productId) desc limit 3"
    )
    List<OrderProductRankResponse> findTop3RankProductsByCount();

    @Query("select new com.example.hanghaeplus.presentation.product.response.OrderProductRankResponse(o.productId,p.name,count(o.productId)) " +
            "from OrderLine o inner join Product p " +
            "on o.productId = p.id " +
            "where o.createdDate >= :startDate and o.createdDate < :endDate " +
            "group by o.productId  " +
            "order by count(o.productId) desc limit 3"
    )
    List<OrderProductRankResponse> findTop3RankProductsInLast3Days(@Param("startDate") LocalDateTime startDate , @Param("endDate") LocalDateTime endDate);

}
