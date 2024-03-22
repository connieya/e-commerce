package com.example.hanghaeplus.infrastructure.user;

import com.example.hanghaeplus.application.user.UserRepository;
import com.example.hanghaeplus.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public Optional<User> findByName(String name) {
        return userJpaRepository.findByName(name);
    }

    @Override
    public Optional<User> findByIdPessimisticLock(Long userId) {
        return userJpaRepository.findByIdPessimisticLock(userId);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        return userJpaRepository.findByNickname(nickname);
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll();
    }
}
