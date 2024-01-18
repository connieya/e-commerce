package com.example.hanghaeplus.repository.product;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM ProductEntity p WHERE p.id IN :productIds ")
    List<ProductEntity> findAllByPessimisticLock(@Param("productIds") List<Long> productIds);

    @Query(value = "SELECT p FROM Product p WHERE p.id IN :productIds FOR UPDATE ", nativeQuery = true)
    List<ProductEntity> findAllByPessimisticLock2(@Param("productIds") List<Long> productIds);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM ProductEntity p WHERE p.id = :productId ")
    Optional<ProductEntity> findByIdPessimisticLock(@Param("productId") Long productId);
}
