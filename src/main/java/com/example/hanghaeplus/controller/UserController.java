package com.example.hanghaeplus.controller;

import com.example.hanghaeplus.dto.user.UserRegisterRequest;
import com.example.hanghaeplus.result.ResultCode;
import com.example.hanghaeplus.result.ResultResponse;
import com.example.hanghaeplus.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
