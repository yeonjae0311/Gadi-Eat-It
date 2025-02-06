package com.basic.GADI.repository;

import com.basic.GADI.entity.Restaurants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResRepository extends JpaRepository<Restaurants, Long> {
   /* @Query("SELECT DISTINCT r FROM Restaurants r LEFT JOIN FETCH r.ratings WHERE r.resId = :resId")
    @Nonnull
    Optional<Restaurants> findById(@Nonnull Long resId);*/

    @EntityGraph(attributePaths = {"ratings"})
    Optional<Restaurants> findEntityGraphByResId(Long resId);

    @Query("SELECT r FROM Restaurants r")
    Page<Restaurants> findAllRestaurants(Pageable pageable);

    @EntityGraph(attributePaths = {"favorites", "ratings"})
    @Query("SELECT r FROM Restaurants r WHERE r.resId IN :restaurantIds")
    List<Restaurants> findAllWithFavoritesAndRatings(@Param("restaurantIds") List<Long> restaurantIds);

  /*  @Query("SELECT r FROM Restaurants r JOIN r.favorites f WHERE f.user.userId = :userId")
    List<Restaurants> findFavoriteRestaurantsByUserId(@Param("userId") Long userId);*/

    @EntityGraph(attributePaths = {"favorites"})
    Page<Restaurants> findEntityGraphByFavoritesUserUserId(@Param("userId") Long userId, Pageable pageable);
}
