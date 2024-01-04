package com.example.hanghaeplus.repository;

import com.example.hanghaeplus.repository.common.TimeProvider;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class TestTimeProvider implements TimeProvider {
    private LocalDateTime localDateTime;
    @Override
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
