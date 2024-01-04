package com.example.hanghaeplus.service.user;

import com.example.hanghaeplus.dto.user.UserRechargeRequest;
import com.example.hanghaeplus.dto.user.UserRegisterRequest;
import com.example.hanghaeplus.error.ErrorCode;
import com.example.hanghaeplus.error.exception.EntityAlreadyExistException;
import com.example.hanghaeplus.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.repository.point.Point;
import com.example.hanghaeplus.repository.user.User;
import com.example.hanghaeplus.repository.point.PointRepository;
import com.example.hanghaeplus.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import static com.example.hanghaeplus.repository.point.PointTransactionStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PointRepository pointRepository;


    public void registerUser(UserRegisterRequest request) {
        if (userRepository.findByName(request.getName()).isPresent()) {
            throw new EntityAlreadyExistException(ErrorCode.USER_ALREADY_EXIST);
        }
        ;
        User user = User.create(request.getName(), request.getPoint());
        userRepository.save(user);
    }

//    @Transactional
    public void rechargePoint(UserRechargeRequest request) {
        try {
            User user = userRepository.findById(request.getId()).orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
            user.rechargePoint(request.getPoint());
            Point point = Point.create(user, request.getPoint(), RECHARGE);
            pointRepository.save(point);
            userRepository.save(user);
        } catch (ObjectOptimisticLockingFailureException e) {
            log.info("낙관적 락");
            rechargePoint(request);
//            throw new OptimisticLockingFailureException("트랜잭션 수정 충돌");
        }
    }
}
