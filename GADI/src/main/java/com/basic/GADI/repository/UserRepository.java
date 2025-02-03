package com.basic.GADI.repository;

import com.basic.GADI.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserEmail(String userEmail);

    Optional<User> findByUserEmail(String userEmail);

    List<User> findAll();

}
