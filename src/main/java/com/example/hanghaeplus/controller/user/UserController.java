package com.example.hanghaeplus.controller.user;

import com.example.hanghaeplus.controller.user.request.UserRechargeRequest;
import com.example.hanghaeplus.controller.user.request.UserRegisterRequest;
import com.example.hanghaeplus.result.ResultResponse;
import com.example.hanghaeplus.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.hanghaeplus.result.ResultCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @ApiOperation("유저 등록 API")
    @PostMapping
    public ResponseEntity<ResultResponse> registerUser(@RequestBody UserRegisterRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok(ResultResponse.of(USER_POST_SUCCESS));
    }

    @ApiOperation("잔액 충전 API")
    @PostMapping
    public ResponseEntity<ResultResponse> recharge(@RequestBody UserRechargeRequest request){
        userService.rechargePoint(request);
        return ResponseEntity.ok(ResultResponse.of(POINT_POST_SUCCESS));
    }

    @ApiOperation("잔액 조회 API")
    @GetMapping("/{id}")
    public ResponseEntity<ResultResponse> getPoint(@PathVariable("id") Long id){
        return ResponseEntity.ok(ResultResponse.of(POINT_GET_SUCCESS ,userService.getPoint(id)));
    }
}
