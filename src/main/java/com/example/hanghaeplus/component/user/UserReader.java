package com.example.hanghaeplus.component.user;

import com.example.hanghaeplus.error.ErrorCode;
import com.example.hanghaeplus.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.orm.entity.User;
import com.example.hanghaeplus.orm.repository.UserRepository;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
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
