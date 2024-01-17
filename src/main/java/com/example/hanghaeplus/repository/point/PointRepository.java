package com.example.hanghaeplus.repository.point;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<PointLine,Long> {

    PointLine findByUserId(Long userId);
}
