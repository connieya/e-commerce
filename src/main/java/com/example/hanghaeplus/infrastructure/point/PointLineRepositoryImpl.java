package com.example.hanghaeplus.infrastructure.point;

import com.example.hanghaeplus.application.point.PointLineRepository;
import com.example.hanghaeplus.domain.point.PointLine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PointLineRepositoryImpl implements PointLineRepository {

    private final PointLineJpaRepository pointLineJpaRepository;

    @Override
    public PointLine save(PointLine pointLine) {
        return pointLineJpaRepository.save(pointLine);
    }

    @Override
    public PointLine findByUserId(Long userId) {
        return pointLineJpaRepository.findByUserId(userId);
    }

    @Override
    public List<PointLine> findAll() {
        return pointLineJpaRepository.findAll();
    }
}
