package com.example.hanghaeplus.presentation.user;

import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.domain.user.UserTokens;
import com.example.hanghaeplus.presentation.user.request.LoginRequest;
import com.example.hanghaeplus.presentation.user.request.UserRechargeRequest;
import com.example.hanghaeplus.presentation.user.request.UserCreateRequest;
import com.example.hanghaeplus.common.result.ResultResponse;
import com.example.hanghaeplus.application.user.UserService;
import com.example.hanghaeplus.presentation.user.response.UserPointResponse;
import com.example.hanghaeplus.presentation.user.response.UserResponse;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.hanghaeplus.common.result.ResultCode.*;
import static org.springframework.http.HttpHeaders.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserApiController {
    private static final int COOKIE_AGE_SECONDS = 604800;
    private final UserService userService;

    @ApiOperation("로그인")
    @PostMapping("/login")
    public ResponseEntity<ResultResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse
            response) {
        log.info("email = {}  , password = {} ", loginRequest.getEmail(), loginRequest.getPassword());
        UserTokens tokens = userService.login(loginRequest);
        final ResponseCookie cookie = ResponseCookie.from("refresh-token", tokens.getRefreshToken())
                .maxAge(COOKIE_AGE_SECONDS)
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .path("/")
                .build();
        response.addHeader(SET_COOKIE, cookie.toString());
        return ResponseEntity.ok(ResultResponse.of(USER_LIST_GET_SUCCESS, tokens));
    }

    @ApiOperation("유저 등록 API")
    @PostMapping
    public ResponseEntity<ResultResponse> register(@RequestBody UserCreateRequest request) {
        userService.register(request.toCommand());
        return ResponseEntity.ok(ResultResponse.of(USER_POST_SUCCESS));
    }


    @ApiOperation("회원 목록 조회 API")
    @GetMapping("/list")
    public ResponseEntity<ResultResponse> list() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(ResultResponse.of(USER_LIST_GET_SUCCESS, users.stream().map(UserResponse::from).collect(Collectors.toList())));
    }

    @ApiOperation("잔액 충전 API")
    @PostMapping("/point")
    public ResponseEntity<ResultResponse> recharge(@RequestBody UserRechargeRequest request) {
        userService.rechargePoint(request.toCommand());
        return ResponseEntity.ok(ResultResponse.of(POINT_POST_SUCCESS));
    }

    @ApiOperation("잔액 조회 API")
    @GetMapping("/{id}")
    public ResponseEntity<ResultResponse> getPoint(@PathVariable("id") Long id) {
        User user = userService.findUser(id);
        return ResponseEntity.ok(ResultResponse.of(POINT_GET_SUCCESS, UserPointResponse.from(user)));
    }
}
