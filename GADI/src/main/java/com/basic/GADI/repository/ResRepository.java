package com.basic.GADI.repository;

import com.basic.GADI.entity.Restaurants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResRepository extends JpaRepository<Restaurants, Long> {
   /* @Query("SELECT DISTINCT r FROM Restaurants r LEFT JOIN FETCH r.ratings WHERE r.resId = :resId")
    @Nonnull
    Optional<Restaurants> findById(@Nonnull Long resId);*/

    // 레스토랑 단건 조회(별점 포함)
    @EntityGraph(attributePaths = {"ratings"})
    Optional<Restaurants> findByResDeleteAndResId(String resDelete, Long resId);

    // 레스토랑 전체 리스트 조회 1 (레스토랑 전체 리스트)
    @Query("SELECT r FROM Restaurants r")
    Page<Restaurants> findAllRestaurants(Pageable pageable);

    // 레스토랑 전체 리스트 조회 2 (레스토랑 아이디로 즐겨찾기, 별점 조회)
    @EntityGraph(attributePaths = {"favorites", "ratings"})
    List<Restaurants> findByResDeleteAndResIdIn(String resDelete, List<Long> restaurantIds);

  /*  @Query("SELECT r FROM Restaurants r JOIN r.favorites f WHERE f.user.userId = :userId")
    List<Restaurants> findFavoriteRestaurantsByUserId(@Param("userId") Long userId);*/

    // 유저별 즐겨찾기 조회
    @EntityGraph(attributePaths = {"favorites"})
    Page<Restaurants> findByFavoritesUserUserId(Long userId, Pageable pageable);

    // 레스토랑 단건 조회(레스토랑 entity만 포함)
    Optional<Restaurants> findByResId(Long resId);
}
