package com.example.hanghaeplus.common.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LogFilter implements Filter {

    private final Logger LOGGER = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            LOGGER.info("request URI = {} ", requestURI);
            LOGGER.info("method = {}", method);
            chain.doFilter(request, response);
        } catch (Exception e) {
            LOGGER.error("error = {} ");
        } finally {
            LOGGER.info("response status = {}" ,httpResponse.getStatus());
        }

    }
}
