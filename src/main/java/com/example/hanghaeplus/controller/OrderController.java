package com.example.hanghaeplus.controller;

import com.example.hanghaeplus.dto.order.OrderPostRequest;
import com.example.hanghaeplus.orm.entity.Order;
import com.example.hanghaeplus.result.ResultCode;
import com.example.hanghaeplus.result.ResultResponse;
import com.example.hanghaeplus.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;


    @ApiOperation("주문 API")
    @PostMapping
    public ResponseEntity<ResultResponse> createOrder(@RequestBody List<OrderPostRequest> request){
        orderService.createOrder(request);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.ORDER_POST_SUCCESS));
    }

}
