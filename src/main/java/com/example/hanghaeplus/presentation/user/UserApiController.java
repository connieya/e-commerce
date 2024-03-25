package com.example.hanghaeplus.presentation.user;

import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.presentation.user.request.UserRechargeRequest;
import com.example.hanghaeplus.presentation.user.request.UserCreateRequest;
import com.example.hanghaeplus.common.result.ResultResponse;
import com.example.hanghaeplus.application.user.UserService;
import com.example.hanghaeplus.presentation.user.response.UserPointResponse;
import com.example.hanghaeplus.presentation.user.response.UserResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.hanghaeplus.common.result.ResultCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {

    private final UserService userService;

    @ApiOperation("유저 등록 API")
    @PostMapping
    public ResponseEntity<ResultResponse> register(@RequestBody UserCreateRequest request) {
        userService.register(request.toCommand());
        return ResponseEntity.ok(ResultResponse.of(USER_POST_SUCCESS));
    }


    @ApiOperation("회원 목록 조회 API")
    @GetMapping("/list")
    public ResponseEntity<ResultResponse> list(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok(ResultResponse.of(USER_LIST_GET_SUCCESS,users.stream().map(UserResponse::from).collect(Collectors.toList())));
    }

    @ApiOperation("잔액 충전 API")
    @PostMapping("/point")
    public ResponseEntity<ResultResponse> recharge(@RequestBody UserRechargeRequest request){
        userService.rechargePoint(request.toCommand());
        return ResponseEntity.ok(ResultResponse.of(POINT_POST_SUCCESS));
    }

    @ApiOperation("잔액 조회 API")
    @GetMapping("/{id}")
    public ResponseEntity<ResultResponse> getPoint(@PathVariable("id") Long id){
        User user = userService.findUser(id);
        return ResponseEntity.ok(ResultResponse.of(POINT_GET_SUCCESS , UserPointResponse.from(user)));
    }
}
