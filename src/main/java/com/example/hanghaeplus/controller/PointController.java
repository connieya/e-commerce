package com.example.hanghaeplus.controller;

import com.example.hanghaeplus.dto.point.PointGetResponse;
import com.example.hanghaeplus.dto.point.PointPostRequest;
import com.example.hanghaeplus.orm.entity.Point;
import com.example.hanghaeplus.result.ResultCode;
import com.example.hanghaeplus.result.ResultResponse;
import com.example.hanghaeplus.service.PointService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.hanghaeplus.result.ResultCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointController {

    private final PointService pointService;

    @ApiOperation("잔액 조회 API")
    @GetMapping("/{name}")
    public ResponseEntity<ResultResponse> getPoint(@PathVariable("name") String name){
        return null;
//        return ResponseEntity.ok(ResultResponse.of(PAYMENT_GET_SUCCESS ,point));
    }

    @ApiOperation("잔액 충전 API")
    @PostMapping
    public ResponseEntity<ResultResponse> recharge(@RequestBody PointPostRequest request){
//        pointService.recharge(request);
//        return ResponseEntity.ok(ResultResponse.of(PAYMENT_POST_SUCCESS));
        return null;
    }

}
