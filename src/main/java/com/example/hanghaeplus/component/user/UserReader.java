package com.example.hanghaeplus.component.user;

import com.example.hanghaeplus.error.ErrorCode;
import com.example.hanghaeplus.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.orm.entity.User;
import com.example.hanghaeplus.orm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.hanghaeplus.error.ErrorCode.*;

@Component
@RequiredArgsConstructor
public class UserReader {

    private final UserRepository userRepository;

    public User read(Long userid) {
        return userRepository.findById(userid).orElseThrow(()->new EntityNotFoundException(USER_NOT_FOUND));
    }
}
