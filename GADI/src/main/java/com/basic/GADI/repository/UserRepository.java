package com.basic.GADI.repository;

import com.basic.GADI.entity.User;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 유저 이메일 있는지 확인
    boolean existsByUserEmail(String userEmail);

    // 유저이메일로 유저 단건 조회
    Optional<User> findByUserEmail(String userEmail);

    // 유저 id로 유저 단건 조회
    Optional<User> findByUserId(Long userId);

    // 유저 리스트 전체 조회
    @Nonnull
    List<User> findAll();

}
