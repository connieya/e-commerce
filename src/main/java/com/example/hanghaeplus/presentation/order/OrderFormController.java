package com.example.hanghaeplus.presentation.order;

import com.example.hanghaeplus.application.order.OrderService;
import com.example.hanghaeplus.application.product.ProductService;
import com.example.hanghaeplus.domain.order.Order;
import com.example.hanghaeplus.domain.order.OrderLine;
import com.example.hanghaeplus.domain.product.Product;
import com.example.hanghaeplus.presentation.order.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderFormController {

    private final ProductService productService;
    private final OrderService orderService;

    @ModelAttribute("productList")
    public List<OrderProductForm> productForms() {
        List<Product> products = productService.findAll();
        return products
                .stream()
                .map(OrderProductForm::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/add")
    public String addForm(){
        return "order/add";
    }

    @GetMapping("/list")
    public String listForm(Model model){
        List<Order> orderList = orderService.getOrderList();
        model.addAttribute("orderList",orderList
                .stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList()));
        return "order/list";
    }
}
