package com.example.hanghaeplus.infrastructure.user;

import com.example.hanghaeplus.domain.user.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String  name);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from User u where u.id = :userId")
    Optional<User> findByIdPessimisticLock(@Param("userId") Long userId);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);
}
