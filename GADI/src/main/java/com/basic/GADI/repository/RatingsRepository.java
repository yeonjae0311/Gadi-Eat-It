package com.basic.GADI.repository;

import com.basic.GADI.entity.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingsRepository extends JpaRepository<Ratings, Long> {
    List<Ratings> findByRestaurants_ResId(Long resId);
}
