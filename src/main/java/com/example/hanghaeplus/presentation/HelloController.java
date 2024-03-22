package com.example.hanghaeplus.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class HelloController {




    @GetMapping("/")
    public String hello() {
        return "index";
    }


}
