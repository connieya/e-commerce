package com.example.hanghaeplus.orm.repository;

import com.example.hanghaeplus.orm.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point ,Long> {
}
