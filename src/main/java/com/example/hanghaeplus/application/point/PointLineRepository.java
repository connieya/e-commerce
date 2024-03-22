package com.example.hanghaeplus.application.point;

import com.example.hanghaeplus.domain.point.PointLine;

import java.util.List;

public interface PointLineRepository {

    PointLine save(PointLine pointLine);
    PointLine findByUserId(Long userId);
    List<PointLine> findAll();


}
