package com.example.hanghaeplus.controller.order;

import com.example.hanghaeplus.controller.order.request.OrderPostRequest;
import com.example.hanghaeplus.common.result.ResultCode;
import com.example.hanghaeplus.common.result.ResultResponse;
import com.example.hanghaeplus.controller.order.response.OrderPostResponse;
import com.example.hanghaeplus.controller.product.response.OrderProductRankResponse;
import com.example.hanghaeplus.repository.order.Order;
import com.example.hanghaeplus.service.order.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;


    @ApiOperation("주문 API")
    @PostMapping
    public ResponseEntity<ResultResponse> createOrder(@RequestBody OrderPostRequest request) {
        Order order = orderService.create(request.toCommand());
        return ResponseEntity.ok(ResultResponse.of(ResultCode.ORDER_POST_SUCCESS, OrderPostResponse.of(order)));
    }

    @ApiOperation("상위 상품 조회 API")
    @GetMapping("/rank")
    public List<OrderProductRankResponse> getTopProduct(){
        return orderService.getRankProduct();
    }

}
