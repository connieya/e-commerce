package com.example.hanghaeplus.application.user;

import com.example.hanghaeplus.domain.user.User;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByName(String  name);

    Optional<User> findByIdPessimisticLock(@Param("userId") Long userId);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    List<User> findAll();
}
