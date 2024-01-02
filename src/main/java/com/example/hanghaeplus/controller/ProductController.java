package com.example.hanghaeplus.controller;

import com.example.hanghaeplus.dto.product.ProductGetResponse;
import com.example.hanghaeplus.dto.product.ProductPostRequest;
import com.example.hanghaeplus.result.ResultResponse;
import com.example.hanghaeplus.service.product.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.hanghaeplus.result.ResultCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;


    @ApiOperation("상품 등록 API")
    @PostMapping
    public ResponseEntity<ResultResponse> registerProduct(@RequestBody ProductPostRequest request){
        productService.registerProduct(request);
        return ResponseEntity.ok(ResultResponse.of(PRODUCT_POST_SUCCESS));
    }

    @ApiOperation("상품 조회 API")
    @GetMapping("/{productId}")
    public ResponseEntity<ResultResponse> getProduct(@PathVariable(name = "productId") Long productId) {
        ProductGetResponse product = productService.getProduct(productId);
        return ResponseEntity.ok(ResultResponse.of(PRODUCT_GET_SUCCESS ,product));
    }

    @ApiOperation("상위 상품 조회 API")
    @GetMapping("/rank")
    public ResponseEntity<ResultResponse> getTopProduct(){
        productService.getRankProduct();
        return null;
    }
}
