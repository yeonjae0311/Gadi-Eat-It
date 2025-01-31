package com.basic.GADI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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

}
