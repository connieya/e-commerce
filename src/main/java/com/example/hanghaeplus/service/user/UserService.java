package com.example.hanghaeplus.service.user;

import com.example.hanghaeplus.controller.user.response.UserPointResponse;
import com.example.hanghaeplus.common.error.exception.EntityAlreadyExistException;
import com.example.hanghaeplus.common.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.repository.point.PointLine;
import com.example.hanghaeplus.repository.point.PointRepository;
import com.example.hanghaeplus.repository.user.UserEntity;
import com.example.hanghaeplus.repository.user.UserRepository;
import com.example.hanghaeplus.service.user.request.UserCreate;
import com.example.hanghaeplus.service.user.request.UserRecharge;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.hanghaeplus.common.error.ErrorCode.*;
import static com.example.hanghaeplus.repository.point.PointTransactionStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PointRepository pointRepository;

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException(USER_NOT_FOUND));
    }

    public User findByIdPessimisticLock(Long userId){
        return userRepository.findByIdPessimisticLock(userId).orElseThrow(()-> new EntityNotFoundException(USER_NOT_FOUND));
    }


    @Transactional
    public void save(UserCreate userCreate) {
        if (userRepository.findByName(userCreate.getName()).isPresent()) {
            throw new EntityAlreadyExistException(USER_ALREADY_EXIST);
        }

        User user = User.create(userCreate);
        userRepository.save(user);
    }

    @Transactional
    public void rechargePoint(UserRecharge userRecharge) {
        try {
            User user = findById(userRecharge);
            user.rechargePoint(userRecharge.getPoint());
            PointLine pointLine = PointLine.create(UserEntity.from(user), userRecharge.getPoint(), RECHARGE);
            pointRepository.save(pointLine);
            userRepository.save(user);
        } catch (ObjectOptimisticLockingFailureException e) {
            log.info("낙관적 락");
            rechargePoint(userRecharge);
        }
    }

    private User findById(UserRecharge request) {
        return userRepository.findById(request.getId()).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }

    public UserPointResponse getPoint(Long id) {
        return null;
    }
}
