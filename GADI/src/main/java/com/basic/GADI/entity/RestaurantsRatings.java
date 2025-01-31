package com.basic.GADI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Entity
public class RestaurantsRatings {
    @Id
    @ManyToOne
    @JoinColumn(name = "resId")
    private Restaurants restaurants;

    @Id
    @ManyToOne
    @JoinColumn(name = "ratId")
    private Ratings ratings;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantsRatings that = (RestaurantsRatings) o;
        return Objects.equals(restaurants, that.restaurants) && Objects.equals(ratings, that.ratings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurants, ratings);
    }
}
