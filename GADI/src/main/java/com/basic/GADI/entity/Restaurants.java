package com.basic.GADI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@Entity
public class Restaurants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resId;

    @Column(nullable = false)
    private String resName;

    @Column(nullable = false)
    private String resAddress;

    @Column
    private String resSector;

    @Column
    private String resPhone;

    @Column
    private String resPhoto;

    @ColumnDefault("'N'")
    private String resDelete;

    @OneToMany(mappedBy = "restaurants", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Ratings> ratings;

    @OneToMany(mappedBy = "restaurants", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorites> favorites;

}
