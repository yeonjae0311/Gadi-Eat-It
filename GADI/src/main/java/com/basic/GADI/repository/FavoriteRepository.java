package com.basic.GADI.repository;

import com.basic.GADI.entity.Favorites;
import com.basic.GADI.entity.Restaurants;
import com.basic.GADI.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface FavoriteRepository extends JpaRepository<Favorites, Long> {

    Page<Favorites> findByUserUserId(Long userId, Pageable pageable);

    boolean existsByUserAndRestaurants(User user, Restaurants restaurants);



    Favorites findByUser_UserIdAndRestaurants_resId(Long userId, Long resId);
}

