package com.example.hanghaeplus.presentation.coupon;

import com.example.hanghaeplus.application.coupon.CouponService;
import com.example.hanghaeplus.domain.coupon.Coupon;
import com.example.hanghaeplus.presentation.coupon.request.CouponPostRequest;
import com.example.hanghaeplus.presentation.coupon.response.CouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/coupon")
@RequiredArgsConstructor
@Controller
public class CouponFormController {

    private final CouponService couponService;

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("couponForm", new CouponPostRequest());
        return "coupon/add";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute CouponPostRequest couponPostRequest){
        couponService.save(couponPostRequest.toDomain(UUID.randomUUID().toString()));
        return "redirect:/";
    }

    @GetMapping("/list")
    public String listForm(Model model){
        List<Coupon> coupons = couponService.findAll();
        model.addAttribute("couponList",coupons
                .stream()
                .map(CouponResponse::from)
                .collect(Collectors.toList()));
        return "coupon/list";
    }
}
