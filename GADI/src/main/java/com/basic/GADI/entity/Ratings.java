package com.basic.GADI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Ratings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratId;

    @Column(nullable = false)
    private Double score;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "resId")
    @JsonIgnore
    private Restaurants restaurants;

}
