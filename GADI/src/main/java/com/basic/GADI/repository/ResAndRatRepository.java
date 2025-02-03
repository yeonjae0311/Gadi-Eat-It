package com.basic.GADI.repository;

import com.basic.GADI.entity.RestaurantsRatings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResAndRatRepository extends JpaRepository<RestaurantsRatings, Long> {

}
