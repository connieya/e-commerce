package com.example.hanghaeplus.service;

import com.example.hanghaeplus.dto.user.UserRegisterRequest;
import com.example.hanghaeplus.error.ErrorCode;
import com.example.hanghaeplus.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.orm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public void registerUser(UserRegisterRequest request) {
        userRepository.findByName(request.getName()).orElseThrow(()-> new EntityNotFoundException(ErrorCode.METHOD_NOT_ALLOWED));
    }
}
