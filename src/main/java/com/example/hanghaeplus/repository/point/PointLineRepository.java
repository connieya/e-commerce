package com.example.hanghaeplus.repository.point;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PointLineRepository extends JpaRepository<PointLineEntity,Long> {

    PointLineEntity findByUserId(Long userId);
}
