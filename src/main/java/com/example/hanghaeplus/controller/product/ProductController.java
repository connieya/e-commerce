package com.example.hanghaeplus.controller.product;

import com.example.hanghaeplus.controller.product.request.ProductQuantityRequest;
import com.example.hanghaeplus.controller.product.response.OrderProductRankResponse;
import com.example.hanghaeplus.controller.product.response.ProductGetResponse;
import com.example.hanghaeplus.controller.product.request.ProductPostRequest;
import com.example.hanghaeplus.common.result.ResultResponse;
import com.example.hanghaeplus.service.product.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.hanghaeplus.common.result.ResultCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;


    @ApiOperation("상품 등록 API")
    @PostMapping
    public ResponseEntity<ResultResponse> save(@RequestBody ProductPostRequest request){
        productService.save(request.toCommand());
        return ResponseEntity.ok(ResultResponse.of(PRODUCT_POST_SUCCESS));
    }

    @ApiOperation("재고 추가 API")
    @PostMapping
    public ResponseEntity<ResultResponse> addStock(@RequestBody ProductQuantityRequest productQuantityRequest) {
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
    public List<OrderProductRankResponse> getTopProduct(){
       return productService.getRankProduct();
    }
}
