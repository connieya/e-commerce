package com.example.hanghaeplus.common.config;

import com.example.hanghaeplus.infrastructure.user.BCryptPasswordEncoder;
import com.example.hanghaeplus.infrastructure.user.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
