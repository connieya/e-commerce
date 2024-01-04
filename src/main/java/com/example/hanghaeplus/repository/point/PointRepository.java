package com.example.hanghaeplus.repository.point;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point ,Long> {

    Point findByUserId(Long userId);
}
