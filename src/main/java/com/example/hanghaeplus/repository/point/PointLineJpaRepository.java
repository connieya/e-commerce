package com.example.hanghaeplus.repository.point;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PointLineJpaRepository extends JpaRepository<PointLineEntity,Long> {

    PointLineEntity findByUserId(Long userId);
}
