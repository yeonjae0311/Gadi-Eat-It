package com.basic.GADI.entity;

import jakarta.persistence.*;

@Entity
public class Ratings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratId;

    @Column(nullable = false)
    private Double score;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "resId")
    private Restaurants restaurants;

}
