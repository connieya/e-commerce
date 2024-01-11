package com.example.hanghaeplus.common.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.UUID;

public class LogFilter implements Filter {

    private final Logger LOGGER = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();
        MDC.put("traceId", UUID.randomUUID().toString().substring(0, 8));
        HttpServletResponse httpResponse = (HttpServletResponse) response;


        LOGGER.info("[{}]  request URI = {} ", MDC.get("traceId"), requestURI);
        LOGGER.info("[{}]  method = {}", MDC.get("traceId"), method);
        chain.doFilter(request, response);

        LOGGER.info("[{}]  response status = {}", MDC.get("traceId"), httpResponse.getStatus());
        MDC.clear();


    }
}
