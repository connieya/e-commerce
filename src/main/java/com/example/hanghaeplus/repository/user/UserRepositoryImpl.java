package com.example.hanghaeplus.repository.user;

import com.example.hanghaeplus.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.from(user)).toDomain();
    }

    @Override
    public Optional<User> findByName(String name) {
        return userJpaRepository.findByName(name).map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id).map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findByIdPessimisticLock(Long id) {
        return userJpaRepository.findByIdPessimisticLock(id).map(UserEntity::toDomain);
    }

    @Override
    public void deleteAll() {
        userJpaRepository.deleteAll();
    }
}
