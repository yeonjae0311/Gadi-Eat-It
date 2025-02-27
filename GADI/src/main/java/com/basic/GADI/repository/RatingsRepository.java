package com.basic.GADI.repository;

import com.basic.GADI.entity.Ratings;
import com.basic.GADI.entity.Restaurants;
import com.basic.GADI.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RatingsRepository extends JpaRepository<Ratings, Long> {
    Ratings findByUserAndRestaurants(User user, Restaurants restaurants);

    @Query("SELECT COALESCE(AVG(r.score), 0) FROM Ratings r WHERE r.restaurants.resId = :resId")
    Double findAverageScoreByResId(@Param("resId") Long resId);
}
