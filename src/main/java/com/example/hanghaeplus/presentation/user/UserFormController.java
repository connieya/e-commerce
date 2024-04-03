package com.example.hanghaeplus.presentation.user;

import com.example.hanghaeplus.application.user.UserService;
import com.example.hanghaeplus.common.result.ResultResponse;
import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.presentation.user.request.UserCreateRequest;
import com.example.hanghaeplus.presentation.user.request.UserRechargeRequest;
import com.example.hanghaeplus.presentation.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.hanghaeplus.common.result.ResultCode.POINT_POST_SUCCESS;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserFormController {

    private final UserService userService;

    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("userForm", new UserCreateRequest());
        return "user/add";
    }

    @PostMapping("/add")
    public String register(@ModelAttribute UserCreateRequest request) {
        userService.register(request.toCommand());
        return "redirect:/";
    }

    @GetMapping("/list")
    public String list(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("userList", users.stream().map(UserResponse::from).collect(Collectors.toList()));
        return "user/list";
    }

    @GetMapping("/recharge/point")
    public String rechargePoint(Model model){
        model.addAttribute("pointForm", new UserRechargeRequest());
        return "user/point";
    }

    @PostMapping("/recharge/point")
    public String recharge(@ModelAttribute UserRechargeRequest request){
        userService.rechargePoint(request.toCommand());
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(){
        return "user/login";
    }



}
