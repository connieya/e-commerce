package com.example.hanghaeplus.repository.user;

import com.example.hanghaeplus.domain.user.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByName(String name);

    Optional<User> findById(Long id);

    Optional<User> findByIdPessimisticLock(Long id);
}
