package com.example.hanghaeplus.presentation.product;

import com.example.hanghaeplus.application.product.ProductService;
import com.example.hanghaeplus.application.product.result.ProductResponse;
import com.example.hanghaeplus.domain.product.Product;
import com.example.hanghaeplus.domain.product.ProductCategory;
import com.example.hanghaeplus.presentation.product.request.ProductPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductFormController {

    private final ProductService productService;

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("productForm", new ProductPostRequest());
        return "product/add";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute ProductPostRequest productPostRequest) {
        productService.register(productPostRequest.toCommand());
        return "redirect:/";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products.stream().map(ProductResponse::from).collect(Collectors.toList()));
        return "product/list";
    }

    @GetMapping("/add/quantity/{productId}")
    public String quantityForm(Model model, @PathVariable("productId") Long productId) {
        model.addAttribute("productId", productId);
        return "product/quantity";
    }

    @GetMapping("/category")
    public String categoryForm() {
        return "product/category";
    }

    @ModelAttribute("categoryList")
    public List<ProductCategory> categoryList(){
        return productService.getProductCategoryList();
    }

    @GetMapping("/category/list")
    public String categoryListForm(){
        return "product/category-list";
    }

}
