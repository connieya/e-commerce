package com.example.hanghaeplus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    private final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);


    @GetMapping("/")
    public String hello() {
        LOGGER.info("[{}]  Hello Controller ", MDC.get("traceId"));
        LOGGER.error("ERROR");
        return "hello world ";
    }

    @GetMapping("/hello")
    public void hello2() {
        LOGGER.trace("Trace Log");
        LOGGER.debug("Debug Log");
        LOGGER.info("Info Log");
        LOGGER.warn("Warn Log");
        LOGGER.error("Error Log");
    }
}
