package com.example.hanghaeplus.service;

import com.example.hanghaeplus.dto.user.UserRegisterRequest;
import com.example.hanghaeplus.error.ErrorCode;
import com.example.hanghaeplus.error.exception.EntityAlreadyExistException;
import com.example.hanghaeplus.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.orm.entity.Point;
import com.example.hanghaeplus.orm.entity.User;
import com.example.hanghaeplus.orm.repository.PointRepository;
import com.example.hanghaeplus.orm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public void registerUser(UserRegisterRequest request) {
        if(userRepository.findByName(request.getName()).isPresent()){
            throw new EntityAlreadyExistException(ErrorCode.USER_ALREADY_EXIST);
        };
        User user = new User(request.getName());
        user.addPoint();
        userRepository.save(user);
    }
}
