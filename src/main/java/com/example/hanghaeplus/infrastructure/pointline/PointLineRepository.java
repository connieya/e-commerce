package com.example.hanghaeplus.infrastructure.pointline;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PointLineRepository extends JpaRepository<PointLine,Long> {

    PointLine findByUserId(Long userId);
}
