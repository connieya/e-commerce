package com.example.hanghaeplus.service.point;

import com.example.hanghaeplus.dto.point.PointGetResponse;
import com.example.hanghaeplus.dto.point.PointPostRequest;
import com.example.hanghaeplus.error.ErrorCode;
import com.example.hanghaeplus.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.error.exception.user.InsufficientPointsException;
import com.example.hanghaeplus.orm.entity.Order;
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


    public void processPayment(Order order) {
        Long totalPrice = order.getTotalPrice();
        User user = userRepository.findById(order.getUser().getId()).get();
        if (user.getCurrentPoint() < totalPrice){
            throw new InsufficientPointsException(INSUFFICIENT_POINT);
        }
        Point point = Point.create(user, totalPrice);
        pointRepository.save(point);
    }
}
