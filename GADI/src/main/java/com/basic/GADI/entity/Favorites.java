package com.basic.GADI.entity;

import jakarta.persistence.*;

@Entity
public class Favorites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "resId")
    private Restaurants restaurants;
}
