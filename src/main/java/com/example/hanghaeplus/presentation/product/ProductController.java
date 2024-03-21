package com.example.hanghaeplus.presentation.product;

import com.example.hanghaeplus.domain.product.Product;
import com.example.hanghaeplus.presentation.product.request.ProductQuantityRequest;
import com.example.hanghaeplus.presentation.product.response.ProductGetResponse;
import com.example.hanghaeplus.presentation.product.request.ProductPostRequest;
import com.example.hanghaeplus.common.result.ResultResponse;
import com.example.hanghaeplus.application.product.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/quantity")
    public ResponseEntity<ResultResponse> addQuantity(@RequestBody ProductQuantityRequest productQuantityRequest) {
        productService.addQuantity(productQuantityRequest.toCommand());
        return ResponseEntity.ok(ResultResponse.of(PRODUCT_ADD_STOCK_SUCCESS));
    }

    @ApiOperation("상품 조회 API")
    @GetMapping("/{productId}")
    public ResponseEntity<ResultResponse> getProduct(@PathVariable(name = "productId") Long productId) {
        Product product = productService.getProduct(productId);
        return ResponseEntity.ok(ResultResponse.of(PRODUCT_GET_SUCCESS ,ProductGetResponse.from(product)));
    }


}
