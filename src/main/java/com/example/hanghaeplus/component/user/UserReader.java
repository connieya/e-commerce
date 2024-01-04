package com.example.hanghaeplus.component.user;

import com.example.hanghaeplus.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.repository.user.User;
import com.example.hanghaeplus.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.example.hanghaeplus.error.ErrorCode.*;

@Component
@RequiredArgsConstructor
public class UserReader {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User read(Long userid) {
//        return userRepository.findById(userid).orElseThrow(()->new EntityNotFoundException(USER_NOT_FOUND));
        return userRepository.findByIdPessimisticLock(userid).orElseThrow(()->new EntityNotFoundException(USER_NOT_FOUND));
    }
}
