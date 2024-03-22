package com.example.hanghaeplus.infrastructure.point;

import com.example.hanghaeplus.domain.point.PointLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointLineJpaRepository extends JpaRepository<PointLine,Long> {

    PointLine findByUserId(Long userId);
}
