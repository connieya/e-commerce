package com.example.hanghaeplus.presentation.order;

import com.example.hanghaeplus.application.order.PopularProductService;
import com.example.hanghaeplus.presentation.order.request.OrderPostRequest;
import com.example.hanghaeplus.common.result.ResultCode;
import com.example.hanghaeplus.common.result.ResultResponse;
import com.example.hanghaeplus.presentation.order.response.OrderPostResponse;
import com.example.hanghaeplus.domain.order.PopularProduct;
import com.example.hanghaeplus.domain.order.Order;
import com.example.hanghaeplus.application.order.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/order")
public class OrderApiController {

    private final OrderService orderService;
    private final PopularProductService popularProductService;

    @ApiOperation("주문 API")
    @PostMapping
    public ResponseEntity<ResultResponse> createOrder(@RequestBody OrderPostRequest request) {
        log.info("주문 API 요청");
        Order order = orderService.create(request.toCommand());
        return ResponseEntity.ok(ResultResponse.of(ResultCode.ORDER_POST_SUCCESS, OrderPostResponse.of(order)));
    }

    @ApiOperation("상위 상품 조회 API")
    @GetMapping("/rank")
    public List<PopularProduct> getTopProduct(){
        return popularProductService.getPopularProduct();
    }

}
