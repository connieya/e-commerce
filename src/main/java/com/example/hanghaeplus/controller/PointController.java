package com.example.hanghaeplus.controller;

import com.example.hanghaeplus.result.ResultCode;
import com.example.hanghaeplus.result.ResultResponse;
import com.example.hanghaeplus.service.PointService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.hanghaeplus.result.ResultCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointController {

    private PointService pointService;

    @ApiOperation("잔액 조회 API")
    @GetMapping
    public ResponseEntity<ResultResponse> getPoint(){
        return ResponseEntity.ok(ResultResponse.of(PAYMENT_GET_SUCCESS));
    }

    @ApiOperation("잔액 충전 API")
    @PostMapping
    public ResponseEntity<ResultResponse> recharge(){
        return ResponseEntity.ok(ResultResponse.of(PAYMENT_POST_SUCCESS));
    }
}
