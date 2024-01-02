package com.example.hanghaeplus.orm;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class TestTimeProvider implements TimeProvider{
    private LocalDateTime localDateTime;
    @Override
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
