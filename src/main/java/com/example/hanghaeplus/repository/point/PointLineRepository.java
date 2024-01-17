package com.example.hanghaeplus.repository.point;

import com.example.hanghaeplus.domain.point.PointLine;

public interface PointLineRepository {

    PointLine findByUserId(Long userId);

    PointLine save(PointLine pointLine);
}
