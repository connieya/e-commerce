package com.example.hanghaeplus.application.user;

import com.example.hanghaeplus.presentation.user.response.UserPointResponse;
import com.example.hanghaeplus.common.error.exception.EntityAlreadyExistException;
import com.example.hanghaeplus.common.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.infrastructure.pointline.PointLine;
import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.infrastructure.pointline.PointLineRepository;
import com.example.hanghaeplus.application.user.command.UserCreate;
import com.example.hanghaeplus.application.user.command.UserRecharge;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.hanghaeplus.common.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PointLineRepository pointRepository;

    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException(USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public User findByIdPessimisticLock(Long userId){
        return userRepository.findByIdPessimisticLock(userId).orElseThrow(()-> new EntityNotFoundException(USER_NOT_FOUND));
    }


    @Transactional
    public void register(UserCreate userCreate) {
        checkUserDuplicate(userCreate);
        User user = User.create(userCreate);
        userRepository.save(user);
    }

    private void checkUserDuplicate(UserCreate userCreate) {
        if (userRepository.findByEmail(userCreate.getEmail()).isPresent()) {
            throw new EntityAlreadyExistException(USER_EMAIL_ALREADY_EXIST);
        }

        if (userRepository.findByNickname(userCreate.getNickname()).isPresent()){
            throw new EntityAlreadyExistException(USER_NICKNAME_ALREADY_EXIST);
        }
    }

    @Transactional
    public void rechargePoint(UserRecharge userRecharge) {
        try {
            User user = findUser(userRecharge.getId());
            user.rechargePoint(userRecharge.getPoint());
            PointLine point = PointLine.create(user, userRecharge.getPoint());
            pointRepository.save(point);
            userRepository.save(user);
        } catch (ObjectOptimisticLockingFailureException e) {
            log.info("낙관적 락");
        }
    }

    @Transactional(readOnly = true)
    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }

}
