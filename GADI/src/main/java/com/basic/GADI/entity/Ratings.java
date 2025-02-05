package com.basic.GADI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    // user와 restaurants가 없는 생성자
    public Ratings(Ratings ratings) {
        this.ratId = ratings.ratId;
        this.score = ratings.score;
    }
}
