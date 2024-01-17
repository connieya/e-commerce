package com.example.hanghaeplus.repository.point;

import com.example.hanghaeplus.domain.point.PointLine;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PointLineRepositoryImpl implements PointLineRepository {

    private final PointLineJpaRepository pointLineJpaRepository;

    @Override
    public PointLine findByUserId(Long userId) {
        return null;
//        return  pointLineJpaRepository.findByUserId(userId);
    }

    @Override
    public PointLine save(PointLine pointLine) {
        return pointLineJpaRepository.save(PointLineEntity.from(pointLine)).toDomain();
    }
}
