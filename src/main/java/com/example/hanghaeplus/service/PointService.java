package com.example.hanghaeplus.service;

import com.example.hanghaeplus.dto.point.PointGetResponse;
import com.example.hanghaeplus.dto.point.PointPostRequest;
import com.example.hanghaeplus.error.ErrorCode;
import com.example.hanghaeplus.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.orm.entity.Point;
import com.example.hanghaeplus.orm.entity.User;
import com.example.hanghaeplus.orm.repository.PointRepository;
import com.example.hanghaeplus.orm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.hanghaeplus.error.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class PointService {

    private final PointRepository pointRepository;
    private final UserRepository userRepository;



    public PointGetResponse getPoint(String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        Point point = pointRepository.findByUserId(user.getId());
        return PointGetResponse.builder()
                .userId(user.getId())
                .point(point.getPoint()).build();
    }

    public void recharge(PointPostRequest request) {
        User user = userRepository.findByName(request.getUsername()).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        Point point = pointRepository.findByUserId(user.getId());
        point.recharge(request.getPoint());
        pointRepository.save(point);
    }
}
