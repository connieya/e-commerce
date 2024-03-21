package com.example.hanghaeplus.infrastructure;

import com.example.hanghaeplus.infrastructure.common.TimeProvider;
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
