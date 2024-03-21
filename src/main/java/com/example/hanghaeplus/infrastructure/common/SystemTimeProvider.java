package com.example.hanghaeplus.infrastructure.common;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SystemTimeProvider implements TimeProvider {
    @Override
    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }
}
