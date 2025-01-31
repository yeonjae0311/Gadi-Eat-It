package com.basic.GADI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Entity
public class RestaurantsFavorites {
    @Id
    @ManyToOne
    @JoinColumn(name = "resId")
    private Restaurants restaurants;

    @Id
    @ManyToOne
    @JoinColumn(name = "favId")
    private Favorites favorites;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantsFavorites that = (RestaurantsFavorites) o;
        return Objects.equals(restaurants, that.restaurants) && Objects.equals(favorites, that.favorites);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurants, favorites);
    }
}
