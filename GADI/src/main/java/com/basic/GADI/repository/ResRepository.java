package com.basic.GADI.repository;

import com.basic.GADI.entity.Restaurants;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResRepository extends JpaRepository<Restaurants, Long> {
    @Nonnull
    List<Restaurants> findAll();

    @Query("SELECT r FROM Restaurants r LEFT JOIN FETCH r.ratings WHERE r.resId = :resId")
    @Nonnull
    Optional<Restaurants> findById(@Nonnull Long resId);

    @Query("SELECT r FROM Restaurants r LEFT JOIN FETCH r.ratings")
    Page<Restaurants> findAllWithRatings(Pageable pageable);

}
