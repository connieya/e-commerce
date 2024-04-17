package com.example.hanghaeplus.application.user;

import com.example.hanghaeplus.common.auth.JwtProvider;
import com.example.hanghaeplus.common.error.exception.EntityAlreadyExistException;
import com.example.hanghaeplus.common.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.domain.point.PointLine;
import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.domain.user.UserTokens;
import com.example.hanghaeplus.infrastructure.point.PointLineJpaRepository;
import com.example.hanghaeplus.application.user.command.UserCreate;
import com.example.hanghaeplus.application.user.command.UserRecharge;
import com.example.hanghaeplus.infrastructure.user.PasswordEncoder;
import com.example.hanghaeplus.presentation.user.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.hanghaeplus.application.user.UserException.*;
import static com.example.hanghaeplus.common.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PointLineJpaRepository pointRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void register(UserCreate userCreate) {
        checkUserDuplicate(userCreate);
        userRepository.save(
                User.create(
                        userCreate.getName(),
                        userCreate.getNickname(),
                        userCreate.getEmail(),
                        passwordEncoder.encode(userCreate.getPassword())
                )
        );
    }

    public UserTokens login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        if (!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())){
            throw new InValidPasswordException(INVALID_PASSWORD);
        }
        return jwtProvider.generateToken(user.getEmail());
    }


    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException(USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public User findByIdPessimisticLock(Long userId){
        return userRepository.findByIdPessimisticLock(userId).orElseThrow(()-> new EntityNotFoundException(USER_NOT_FOUND));
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

    @Transactional(readOnly = true)
    public List<User> findAll() {
       return userRepository.findAll();
    }


}
