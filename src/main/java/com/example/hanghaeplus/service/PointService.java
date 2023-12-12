package com.example.hanghaeplus.service;

import com.example.hanghaeplus.orm.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PointService {

    private final PointRepository pointRepository;

    public void recharge() {

    }
}
