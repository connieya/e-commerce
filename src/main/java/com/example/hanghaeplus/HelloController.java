package com.example.hanghaeplus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "hello world";
    }

    @GetMapping("/hello")
    public String hello2() {
        return "안녕";
    }
}
