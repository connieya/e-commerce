package com.example.hanghaeplus.controller;

import com.example.hanghaeplus.result.ResultCode;
import com.example.hanghaeplus.result.ResultResponse;
import com.example.hanghaeplus.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.hanghaeplus.result.ResultCode.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @ApiOperation("상품 조회 API")
    @GetMapping
    public ResponseEntity<ResultResponse> getProduct() {
        return ResponseEntity.ok(ResultResponse.of(PRODUCT_GET_SUCCESS));
    }

    @ApiOperation("상위 상품 조회 API")
    @GetMapping("/rank")
    public ResponseEntity<ResultResponse> getTopProduct(){
        return null;
    }
}
